let gridApi;

$(document).ready(async function () {
  await loadSelects();
  initGrid();
  loadData();
});

async function loadSelects() {
  // Cargar Combo Deporte
  await fetch("/picks/loadSports")
    .then((response) => response.ok ? response.json() : Promise.reject(response))
    .then((responseJson) => {
      if (responseJson.length > 0) {
        $("#cboFiltroDeporte").empty();
        $("#cboFiltroDeporte").append($("<option>").val("").text("Todos los deportes"));
        responseJson.forEach((sport) => {
          $("#cboFiltroDeporte").append(
            $("<option>").val(sport.id).text(sport.name)
          );
        });
      }
    })
    .catch((error) => console.error("Error al obtener deportes:", error));

  // Cargar Combo Meses (Hardcoded como en Home.js)
  const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
  ];
  $("#cboFiltroMes").empty();
  $("#cboFiltroMes").append($("<option>").val("").text("Todos los meses"));
  meses.forEach(mes => {
    $("#cboFiltroMes").append($("<option>").val(mes).text(mes));
  });

  // Cargar Combo Años (Simple range)
  const currentYear = new Date().getFullYear();
  $("#cboFiltroAnio").empty();
  $("#cboFiltroAnio").append($("<option>").val("").text("Todos los años"));
  for (let i = currentYear; i >= 2023; i--) {
    $("#cboFiltroAnio").append($("<option>").val(i).text(i));
  }
}

function initGrid() {
  const gridOptions = {
    theme: agGrid.themeQuartz.withParams({
      accentColor: 'gray',
      headerBackgroundColor: 'cornflowerblue',
      columnBorder: { width: 1 }
    }),
    autoSizeStrategy: { type: 'fitCellContents' },
    defaultColDef: {
      suppressMovable: true,
      wrapText: true,
      cellStyle: { 'text-align': 'center' }
    },
    rowData: [],
    columnDefs: [
      {
        headerName: 'Fecha',
        field: 'fecha',
        cellRenderer: params => {
          if (!params.value) return "";
          const date = new Date(params.value);
          return date.toISOString().split('T')[0].split('-').reverse().join('/');
        }
      },
      { headerName: 'Total Picks', field: 'totalPicks' },
      { headerName: 'Ganados', field: 'totalWon', cellStyle: { color: 'green', fontWeight: 'bold' } },
      { headerName: 'Perdidos', field: 'totalLost', cellStyle: { color: 'red', fontWeight: 'bold' } },
      { headerName: 'Nulos', field: 'totalVoid' },
      {
        headerName: 'Total Apostado',
        field: 'totalWagered',
        valueFormatter: params => params.value ? `$${params.value.toFixed(2)}` : '$0.00'
      },
      {
        headerName: 'Ganancia Total',
        field: 'totalProfit',
        valueFormatter: params => params.value ? `$${params.value.toFixed(2)}` : '$0.00',
        cellStyle: params => {
          if (params.value > 0) return { color: 'green', fontWeight: 'bold' };
          if (params.value < 0) return { color: 'red', fontWeight: 'bold' };
          return null;
        }
      },
      {
        headerName: '% ROI',
        field: 'roi',
        valueFormatter: params => params.value ? `${params.value.toFixed(2)}%` : '0.00%',
        cellStyle: params => {
          if (params.value > 0) return { color: 'green', fontWeight: 'bold' };
          if (params.value < 0) return { color: 'red', fontWeight: 'bold' };
          return null;
        }
      }
    ],
    getRowStyle: params => {
      if (params.data.totalProfit > 0) {
        return { background: '#d4edda' }; // Light green
      } else if (params.data.totalProfit < 0) {
        return { background: '#f8d7da' }; // Light red
      }
      return null;
    },
    pagination: true,
    paginationPageSize: 50
  };

  const gridDiv = document.getElementById('gridPicksInfo');
  gridApi = agGrid.createGrid(gridDiv, gridOptions);
}

async function loadData() {
  const sportId = $("#cboFiltroDeporte").val();
  const mes = $("#cboFiltroMes").val();
  const year = $("#cboFiltroAnio").val();

  let url = '/picks/loadPicksInfo?';
  if (sportId) url += `sportId=${sportId}&`;
  if (mes) url += `mes=${mes}&`;
  if (year) url += `year=${year}&`;

  $("#gridPicksInfo").LoadingOverlay("show");

  try {
    const response = await fetch(url);
    const data = await response.json();

    // Update Stats
    updateStats(data.stats);

    // Update Grid
    gridApi.setGridOption('rowData', data.gridData);

  } catch (error) {
    console.error("Error loading data:", error);
  } finally {
    $("#gridPicksInfo").LoadingOverlay("hide");
  }
}

function updateStats(stats) {
  // Reset
  $("#txtPlayerMax").text("-");
  $("#txtPlayerMin").text("-");
  $("#txtMarketMax").text("-");
  $("#txtMarketMin").text("-");

  if (stats.mostProfitablePlayer) {
    $("#txtPlayerMax").text(`${stats.mostProfitablePlayer.name} ($${stats.mostProfitablePlayer.value.toFixed(0)})`);
    $("#txtPlayerMax").addClass("text-success").removeClass("text-danger");
  }
  if (stats.leastProfitablePlayer) {
    $("#txtPlayerMin").text(`${stats.leastProfitablePlayer.name} ($${stats.leastProfitablePlayer.value.toFixed(0)})`);
    $("#txtPlayerMin").addClass("text-danger").removeClass("text-success");
  }

  if (stats.mostProfitableMarket) {
    $("#txtMarketMax").text(`${stats.mostProfitableMarket.name} ($${stats.mostProfitableMarket.value.toFixed(0)})`);
    $("#txtMarketMax").addClass("text-success").removeClass("text-danger");
  }
  if (stats.leastProfitableMarket) {
    $("#txtMarketMin").text(`${stats.leastProfitableMarket.name} ($${stats.leastProfitableMarket.value.toFixed(0)})`);
    $("#txtMarketMin").addClass("text-danger").removeClass("text-success");
  }
}

$("#btnFiltrar").click(function () {
  loadData();
});

$("#btnLimpiar").click(function () {
  $("#cboFiltroDeporte").val("");
  $("#cboFiltroMes").val("");
  $("#cboFiltroAnio").val("");
  loadData();
});
