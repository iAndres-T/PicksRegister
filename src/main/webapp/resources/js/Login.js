const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
let URL;

$("#btnLogin").click(function () {
  const inputs = $("input.input-validar").serializeArray();
  const inputs_sin_valor = inputs.filter((item) => item.value.trim() == "");

  if (inputs_sin_valor.length > 0) {
    const mensaje = `Debe completar el campo`;
    toastr.warning("", mensaje);
    $(`input[name="${inputs_sin_valor[0].name}"]`).focus();
    return;
  }

  let user = {};
  user["userName"] = $("#loginUserName").val();
  user["password"] = $("#loginPassword").val();

  fetch("/picks/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  })
    .then(async (response) => {
      const text = await response.text();
      if (text === "Invalid") {
        toastr.warning("", "Usuario o contraseña incorrectos");
      }
      else if (text.endsWith("UpdateMes")) {
        URL = text.split("-")[0].replace("redirect:", "");
        Swal.fire({
          title: "Actualizar Capital",
          text: "El mes ha cambiado, ¿Desea cambiar su Capital?",
          icon: "question",
          showDenyButton: true,
          confirmButtonText: "Modificar mi capital",
          denyButtonText: "Mantener mi capital",
        }).then((result) => {
          if (result.isConfirmed) {
            $("#modalBank").modal("show");
          } else if(result.isDenied) {
            actualizarBank(null);
          }
        });
      }
      else if (text.startsWith("redirect:")) {
        URL = text.replace("redirect:", "");
        window.location.href = URL;
      }
    })
    .catch((error) => {
      toastr.error("", "Error al validar el usuario" + error);
    });
})

$("#btnActualizarBank").click(function () {

  if ($("#txtNuevoCapital").val() == '') {
    toastr.warning("", `Debe completar el campo`);
    $(`#txtNuevoCapital`).focus();
    return;
  }

  actualizarBank($("#txtNuevoCapital").val());
});

async function actualizarBank(bank) {
  let data = {};
  data["userName"] = $("#loginUserName").val();
  data["capital"] = bank;
  data["mes"] = monthNames[new Date().getMonth()];
  data["anio"] = new Date().getFullYear().toString();

  fetch("/picks/actualizarBank", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(data),
  })
    .then(async (response) => {
      if (response.ok) {
        window.location.href = URL;
      }
      else {
        toastr.error("", "Error al actualizar el capital" + text);
        window.location.reload();
      }
    })
    .catch((error) => {
      toastr.error("", "Error al validar el usuario" + error);
    });
}