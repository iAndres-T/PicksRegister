const monthNames = ["Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"];
let URL;

$("#btnLogin").click(function () {
  const inputs = $("input.login-validar").serializeArray();
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
      const text = await response.text();
      if (text === "true") {
        window.location.href = URL;
      }
      else if (text === "Same") {
        toastr.warning("", "El capital es el mismo que el mes anterior");
      }
      else {
        toastr.error("", "Error al actualizar el capital" + text);
        setTimeout(window.location.reload(), 2000);
      }
    })
    .catch((error) => {
      toastr.error("", "Error al validar el usuario" + error);
    });
}

$("#btnRegisterUser").click(function () {
  const inputs = $("input.register-validar").serializeArray();
  const inputs_sin_valor = inputs.filter((item) => item.value.trim() == "");

  if (inputs_sin_valor.length > 0) {
    const mensaje = `Debe completar el campo`;
    toastr.warning("", mensaje);
    $(`input[name="${inputs_sin_valor[0].name}"]`).focus();
    return;
  }

  if ($("#registerPassword").val() !== $("#confirmPassword").val()) {
    toastr.warning("", "Las contraseñas no coinciden");
    $("#registerPassword").focus();
    return;
  }

  let user = {};
  user["userName"] = $("#registerUserName").val();
  user["password"] = $("#registerPassword").val();
  user["capitalInvertido"] = $("#registerCapital").val();
  user["saldoActual"] = $("#registerCapital").val();
  user["saldoInicialMes"] = $("#registerCapital").val();
  user["saldoInicialHistory"] = $("#registerCapital").val();
  user["mesActual"] = monthNames[new Date().getMonth()];
  user["rol"] = {id: 1};

  fetch("/picks/registerUser", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  })
    .then(async (response) => {
      const text = await response.text();
      if (text === "Exists") {
        toastr.warning("", "El UserName ya existe");
      }
      else if (text === "OK") {
        Swal.fire({
          title: "Usuario registrado",
          text: "El usuario ha sido registrado correctamente",
          icon: "success",
          timer: 2000,
          showConfirmButton: false,
        });
        setTimeout(() => {
          window.location.reload();
        }, 4000);
      }
      else {
        toastr.error("", "Error al registrar el usuario");
      }
    })
    .catch((error) => {
      toastr.error("", "Error al validar el usuario");
    });
});