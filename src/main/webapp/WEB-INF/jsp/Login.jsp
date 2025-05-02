<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login y Registro</title>
  <!-- Bootstrap 5 CSS -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css" rel="stylesheet">
  <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet">
</head>

<body class="bg-light" style="background-color: blueviolet !important;">

  <div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card p-4 shadow" style="width: 22rem;">
      <h3 class="text-center mb-4">Iniciar Sesión</h3>
      <form>
        <div class="mb-3">
          <label for="loginUsername" class="form-label">Username</label>
          <input type="text" class="form-control input-validar" id="loginUserName" placeholder="Username">
        </div>
        <div class="mb-3">
          <label for="loginPassword" class="form-label">Password</label>
          <input type="password" class="form-control input-validar" id="loginPassword" placeholder="Password">
        </div>
        <div class="d-grid gap-2">
          <button type="button" class="btn btn-primary" id="btnLogin">Iniciar Sesión</button>
          <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#registerModal">
            Registrarse
          </button>
        </div>
      </form>
    </div>
  </div>

  <!-- Modal de Registro -->
  <div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <form>
          <div class="modal-header">
            <h5 class="modal-title" id="registerModalLabel">Registro</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
          </div>
          <div class="modal-body">
            <div class="mb-3">
              <label for="registerUsername" class="form-label">Username</label>
              <input type="text" class="form-control input-validar" id="registerUsername" placeholder="Username">
            </div>
            <div class="mb-3">
              <label for="registerPassword" class="form-label">Password</label>
              <input type="password" class="form-control input-validar" id="registerPassword" placeholder="Password">
            </div>
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Confirmar Password</label>
              <input type="password" class="form-control input-validar" id="confirmPassword" placeholder="Confirmar Password">
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-success" id="btnRegisterUser">Registrarse</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- Bootstrap 5 JS Bundle -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
  <c:if test="${not empty Script}">
    <script src="${pageContext.request.contextPath}/resources/js/${Script}"></script>
  </c:if>

</body>

</html>