//API Equipos Futbol https://www.sofascore.com/api/v1/sport/football/2025-04-12/-18000/categories
const MODELO_BASE = {
    id: null,
    fecha: "",
    casino: {
        id: 1,
        name: "",
    },
    linea: "",
    sport: {
        id: "",
        name: "",
    },
    country: {
        id: 1,
        name: "",
    },
    equipoLocal: "",
    equipoVisitante: "",
    descripcion: "",
    jugador: "",
    valor: "",
    cuota: "",
    resultado: "Pendiente",
    usuario: {
        id: null,
        userName: "",
    }
};

const meses = [
    "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
    "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
];

let grid;

$(document).ready(function () { 
    loadSelects();
    $("#cboTipoFiltro").val(0).change();
    getGrid();
});

async function getGrid() {
    let picks = await fetch('/picks/loadPicks')
        .then(response => response.json())
        .then(data => {
            return data;
        })
        .catch(error => {
            console.error(error);
        });

    const gridOptions = {
        theme: agGrid.themeQuartz.withParams({
            accentColor: 'gray',
            headerBackgroundColor: 'cornflowerblue',
            columnBorder: { width: 1 }
        }),
        autoSizeStrategy: { type: 'fitCellContents' },
        suppressColumnVirtualisation: true,
        defaultColDef: {
            suppressMovable: true,
            wrapText: true,
            maxWidth: 300,
            cellStyle: { 'line-height': '1.5' }
        },
        getRowId: (params) => {
            return String(params.data.id);
        },
        rowData: picks,
        columnDefs: [
            {
                headerName: 'Editar',
                field: 'edit',
                pinned: 'left',
                cellStyle: { 'text-align': 'center' },
                cellRenderer: params => {
                        return `<button type="button" class="btn btn-outline-info editar-pick"><i class="fa fa-edit"></i></button>`;
                }
            },
            {
                field: 'fecha',
                cellRenderer: params => {
                    const date = new Date(params.value);
                    return date.toISOString().split('T')[0].split('-').reverse().join('/');
                }
            },
            {
                headerName: 'Casa',
                field: 'casino.name',
                filter: true
            },
            {
                field: 'resultado'
            },
            {
                headerName: 'Valor Apostado',
                field: 'valor'
            },
            { field: 'cuota' },
            {
                headerName: 'Pago',
                field: 'pagoPick',
                cellStyle: params => { 
                    if(params.data.resultado === "Acierto") {
                        return { color: 'green', fontWeight: 'bold' };
                    }
                    else if(params.data.resultado === "Perdido") {
                        return { color: 'red', fontWeight: 'bold' };
                    }
                }

            },
            {
                headerName: 'Utilidad',
                field: 'utilidadPick',
                cellStyle: params => { 
                    if(params.data.resultado === "Acierto") {
                        return { color: 'green', fontWeight: 'bold' };
                    }
                    else if(params.data.resultado === "Perdido") {
                        return { color: 'red', fontWeight: 'bold' };
                    }
                }
            },
            {
                headerName: 'Equipo Local',
                field: 'equipoLocal',
                cellStyle: { 'text-align': 'center' },
                filter: true
            },
            {
                field: 'equipoVisitante',
                cellStyle: { 'text-align': 'center' },
                filter: true
            },
            {
                headerName: 'Descripción',
                field: 'descripcion'
            },
            { field: 'jugador', filter: true },
            { headerName: 'Estrategia', field: 'linea' },
            {
                field: 'unidades'
            },
            {
                field: 'probabilidad'
            },
            { field: 'riesgo' },
            { field: 'mes', filter: true },
            {
                headerName: 'Deporte',
                field: 'sport.name',
                filter: true
            },
            {
                headerName: 'País',
                field: 'country.name'
            },
        ],
        pagination: true,
        paginationPageSize: 50,
        paginationPageSizeSelector: [50, 100, 200],
    };
    document.getElementById('gridPicks').innerHTML = '';
    const myGrid = document.getElementById('gridPicks');
    grid = agGrid.createGrid(myGrid, gridOptions);
}

async function loadSelects () {
    // Cargar Combo Casino
    fetch("/picks/loadCasinos")  
        .then((response) => {
            return response.ok ? response.json() : Promise.reject(response);
        })
        .then((responseJson) => {
            if (responseJson.length > 0) {
                $("#cboCasino").empty(); 
                responseJson.forEach((casino) => {
                    $("#cboCasino").append(  
                        $("<option>").val(casino.id).text(casino.name)
                    );
                });
            }
        })
        .catch((error) => {
            console.error("Error al obtener la lista de casinos:", error); 
        });
    
    // Cargar Combo Deporte
    fetch("/picks/loadSports")
        .then((response) => {
            return response.ok ? response.json() : Promise.reject(response);
        })
        .then((responseJson) => {
            if (responseJson.length > 0) {
                $("#cboSport").empty();
                $("#cboFiltroDeporte").empty();
                responseJson.forEach((sport) => {
                    $("#cboSport").append(
                        $("<option>").val(sport.id).text(sport.name)
                    );
                    $("#cboFiltroDeporte").append(
                        $("<option>").val(sport.id).text(sport.name)
                    );
                });
            }
        })
        .catch((error) => {
            console.error("Error al obtener la lista de deportes:", error);
        });

    // Cargar Combo País
    fetch("/picks/loadCountries")
        .then((response) => {
            return response.ok ? response.json() : Promise.reject(response);
        })
        .then((responseJson) => {
            if (responseJson.length > 0) {
                $("#cboCountry").empty();
                responseJson.forEach((country) => {
                    $("#cboCountry").append(
                        $("<option>").val(country.id).text(country.name)
                    );
                });
            }
        })
        .catch((error) => {
            console.error("Error al obtener la lista de países:", error);
        });

    //getTabla();
};

function mostrarModal(modelo = MODELO_BASE) {
    $("#txtId").val(modelo.id);
    $("#txtFecha").val(modelo.fecha == '' ? new Date(new Date().setHours(0, 0, 0, 0)).toISOString().split("T")[0] : modelo.fecha);
    $("#cboCasino").val(modelo.casino.id);
    $("#cboLinea").val(modelo.linea);
    $("#cboSport").val(modelo.sport.id);
    $("#cboCountry").val(modelo.country.id);
    $("#txtEquipoLocal").val(modelo.equipoLocal);
    $("#txtEquipoVisitante").val(modelo.equipoVisitante);
    $("#txtDescripcion").val(modelo.descripcion);
    $("#txtJugador").val(modelo.jugador);
    $("#txtValor").val(modelo.valor);
    $("#txtCuota").val(modelo.cuota);
    $("#cboResultado").val(modelo.resultado);
    $("#modalData").modal("show");
}

$("#btnNuevoPickNBA").click(function () {
    mostrarModal();
    loadLinea("NBA");
    $("#cboSport").val(1);
    loadDescripcion(1);
});

$("#btnNuevoPickMLB").click(function () {
    mostrarModal();
    loadLinea("MLB");
    $("#cboSport").val(2);
    loadDescripcion(2);
});

$("#btnNuevoPickFutbol").click(function () {
    mostrarModal();
    loadLinea("Futbol");
    $("#cboSport").val(3);
    loadDescripcion(3);
    $("#txtJugador").val("No Aplica");
});

function loadLinea(tipo) {
    $("#cboLinea").empty();
    if (tipo === "NBA" || tipo === "MLB") {
        $("#cboLinea").append($("<option>").val("Over").text("Over"));
        $("#cboLinea").append($("<option>").val("Under").text("Under"));
    }
    else if (tipo === "Futbol") { 
        $("#cboLinea").append($("<option>").val("Sencilla").text("Sencilla"));
        $("#cboLinea").append($("<option>").val("Parlay").text("Parlay"));
    }
}

async function loadDescripcion(deporte) {
    $("#cboDescripcion").empty();
    fetch("/picks/loadDetallePicks?deporte=" + deporte)
        .then((response) => {
            return response.ok ? response.json() : Promise.reject(response);
        })
        .then((responseJson) => {
            if (responseJson.length > 0) {
                $("#cboDescripcion").empty();
                responseJson.forEach((descripcion) => {
                    $("#cboDescripcion").append(
                        $("<option>").val(descripcion.tipoPick).text(descripcion.tipoPick)
                    )
                });
            }
        })
        .catch((error) => {
            console.error("Error al obtener la lista de descripciones:", error);
        });
}

$("#btnCloseModal").click(function () {
    $("#modalData").modal("hide");
});

$("#btnGuardarPick").click(function () {
    const inputs = $("input.input-validar").serializeArray();
    const inputs_sin_valor = inputs.filter((item) => item.value.trim() == "");
    const saldoActual = parseFloat($("#saldoActual").text().replace(/[^0-9.-]+/g, ""));

    if (inputs_sin_valor.length > 0) {
        const mensaje = `Debe completar el campo`;
        toastr.warning("", mensaje);
        $(`input[name="${inputs_sin_valor[0].name}"]`).focus();
        return;
    }

    const modelo = structuredClone(MODELO_BASE);
    modelo["id"] = $("#txtId").val() || null;
    modelo["fecha"] = $("#txtFecha").val();
    modelo["casino"]["id"] = $("#cboCasino").val();
    modelo["linea"] = $("#cboLinea").val();
    modelo["sport"]["id"] = $("#cboSport").val();
    modelo["country"]["id"] = $("#cboCountry").val();
    modelo["equipoLocal"] = $("#txtEquipoLocal").val();
    modelo["equipoVisitante"] = $("#txtEquipoVisitante").val();
    modelo["descripcion"] = $("#cboDescripcion").val();
    modelo["jugador"] = $("#txtJugador").val();
    modelo["valor"] = parseFloat($("#txtValor").val());
    modelo["cuota"] = parseFloat($("#txtCuota").val());
    modelo["year"] = $("#txtFecha").val().split("-")[0];
    modelo["mes"] = meses[parseInt($("#txtFecha").val().split("-")[1]) - 1];
    modelo["unidades"] = (modelo["valor"] / saldoActual * 100).toFixed(2);
    modelo["probabilidad"] = (1 / modelo["cuota"] * 100).toFixed(2) + "%";
    modelo["riesgo"] = modelo["unidades"] > 5 ? "Alto" : modelo["unidades"] > 3 ? "Medio" : "Bajo";
    modelo["resultado"] = $("#cboResultado").val();
    if (modelo["resultado"] === "Acierto") {
        modelo["pagoPick"] = (modelo["valor"] * modelo["cuota"]).toFixed(0);
        modelo["utilidadPick"] = (modelo["pagoPick"] - modelo["valor"]).toFixed(0);
    }
    else if (modelo["resultado"] === "Perdido") {
        modelo["pagoPick"] = (modelo["valor"] * (-1)).toFixed(0);
        modelo["utilidadPick"] = modelo["pagoPick"];
    }
    else if (modelo["resultado"] === "Nulo") {
        modelo["pagoPick"] = modelo["valor"];
        modelo["utilidadPick"] = 0;
    }
    modelo["usuario"]["id"] = parseInt($("#userId").text(), 10);

    $("#modalData").find("div.modal-content").LoadingOverlay("show");

    fetch("/picks/registrarPick", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(modelo),
    })
        .then((response) => {
            $("#modalData").find("div.modal-content").LoadingOverlay("hide");
            return response.ok ? response.json() : Promise.reject(response);
        })
        .then((responseJson) => {
            if (responseJson.Estado) {
                $("#modalData").modal("hide");
                getGrid();
                actualizarSaldo();
                swal("Pick registrado", "", "success");
            }
            else {
                swal("Error", "No se pudo registrar el pick " + responseJson.Mensaje, "error");
            }
        })
        .catch((error) => {
            console.error("Error al registrar el pick:", error);
            swal("Error", "Hubo un problema con la solicitud", "error");
        });

});

async function actualizarSaldo() {
    fetch('/picks/getSaldoActual')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al obtener el saldo actual.');
            }
            return response.json();
        })
        .then(data => {
            $('#saldoActual').text(new Intl.NumberFormat('en-CO', { style: 'currency', currency: 'USD' }).format(data));
        })
        .catch(error => {
            console.error(error.message);
        });
}

$('#gridPicks').on('click', '.editar-pick', async function () {
    const rowNode = grid.getRowNode($(this).closest('div.ag-row').attr('row-id'));
    if (rowNode.data.sport.id === 1) {
        loadLinea("NBA");
        await loadDescripcion(1);
    }
    else if (rowNode.data.sport.id === 2) {
        loadLinea("MLB");
        await loadDescripcion(2);
    }
    else {
        loadLinea("Futbol");
        await loadDescripcion(3);
    }
    mostrarModal(rowNode.data);
});

$('#cboTipoFiltro').change(function () {
    const filtro = $(this).val();
    if (filtro == 1) {
        $('#cboFiltroDeporte').show();
        $('#cboFiltroMes').hide();
    }
    else if (filtro == 2) {
        $('#cboFiltroDeporte').hide();
        $('#cboFiltroMes').show();
    }
    else if (filtro == 3) {
        $('#cboFiltroDeporte').show();
        $('#cboFiltroMes').show();
    }
    else {
        $('#cboFiltroDeporte').hide();
        $('#cboFiltroMes').hide();
    }
});

$('#btnLimpiarFiltro').click(function () {
    grid.setFilterModel(null);
    $('#cboTipoFiltro').val(0).change();
    $('#cboFiltroDeporte').val(1);
    $('#cboFiltroMes').val(0);
});

$('#btnFiltrar').click(function () {
    const filtro = $("#cboTipoFiltro").val();
    const filtroDeporte = $("#cboFiltroDeporte option:selected").text();
    let filtroMes = $("#cboFiltroMes").val();

    if (filtro == 1) {      
        grid.setFilterModel({
            'sport.name': { filter: filtroDeporte, type: 'equals' },
        });
    }
    else if (filtro == 2) {
        filtroMes = $("#cboFiltroMes option:selected").text();
        grid.setFilterModel({
            'mes': { filter: filtroMes, type: 'equals' },
        });
    }
    else if (filtro == 3) {
        if (filtroMes != 0) {
            filtroMes = $("#cboFiltroMes option:selected").text();
            grid.setFilterModel({
                'sport.name': { filter: filtroDeporte, type: 'equals' },
                'mes': { filter: filtroMes, type: 'equals' },
            });
        }
        else {
            toastr.warning("", "Seleccione un mes para filtrar");
        }
    }       
});
