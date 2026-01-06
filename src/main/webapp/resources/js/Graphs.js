// Graphs.js: carga datos y renderiza gráficas con Chart.js
$(document).ready(function () {
  // insertar canvas
  $('#graphsContainer').html(`
        <div class="card shadow mb-4">
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-3">
                        <select id="selCasino" class="form-control"><option value="">Todas las casas</option></select>
                    </div>
                    <div class="col-md-3">
                        <select id="selSport" class="form-control"><option value="">Todos los deportes</option></select>
                    </div>
                    <div class="col-md-3">
                        <input id="txtMes" class="form-control" placeholder="Mes (ej: Enero)" />
                    </div>
                    <div class="col-md-3">
                        <button id="btnLoad" class="btn btn-primary w-100">Cargar</button>
                    </div>
                </div>
                <div style="position: relative; height: 75vh; width: 100%">
                    <canvas id="chartLine"></canvas>
                </div>
            </div>
        </div>
    `);

  // poblar selects desde el servidor que ya inyectó listas en JSP
  function populateSelects() {
    if (window.Casinos) {
      window.Casinos.forEach(c => $('#selCasino').append(`<option value="${c.id}">${c.nombre || c.name || c.id}</option>`));
    }
    if (window.Sports) {
      window.Sports.forEach(s => $('#selSport').append(`<option value="${s.id}">${s.nombre || s.name || s.id}</option>`));
    }
  }

  populateSelects();

  let chart;
  function renderChart(labels, data) {
    const ctx = document.getElementById('chartLine').getContext('2d');
    if (chart) chart.destroy();
    chart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: labels,
        datasets: [{
          label: 'Rendimiento acumulado',
          data: data,
          borderColor: '#4e73df',
          backgroundColor: 'rgba(78,115,223,0.05)',
          fill: true,
        }]
      },
      options: {
        maintainAspectRatio: false,
        scales: {
          x: { display: true },
          y: { display: true }
        }
      }
    });
  }

  $('#btnLoad').click(function () {
    const casinoId = $('#selCasino').val();
    const sportId = $('#selSport').val();
    const mes = $('#txtMes').val();
    const base = window.basePath || '';
    let url = base + '/api/graphs/cumulative?';
    if (casinoId) url += `casinoId=${casinoId}&`;
    if (sportId) url += `sportId=${sportId}&`;
    if (mes) url += `mes=${encodeURIComponent(mes)}&`;

    $.get(url).done(function (resp) {
      const labels = Object.keys(resp);
      const data = Object.values(resp).map(v => parseFloat(v.toFixed ? v : v));
      renderChart(labels, data);
    }).fail(function () {
      toastr.error('Error al cargar datos');
    });
  });

  // Cargar inicial
  $('#btnLoad').trigger('click');
});
