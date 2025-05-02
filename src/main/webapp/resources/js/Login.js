$("#btnLogin").click(function () {
  const inputs = $("input.input-validar").serializeArray();
  const inputs_sin_valor = inputs.filter((item) => item.value.trim() == "");
  let URL;

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
        window.location.href = URL;
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