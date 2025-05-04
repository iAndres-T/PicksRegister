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
          <input type="text" class="form-control login-validar" id="loginUserName" placeholder="Username" name="loginUser">
        </div>
        <div class="mb-3">
          <label for="loginPassword" class="form-label">Password</label>
          <input type="password" class="form-control login-validar" id="loginPassword" placeholder="Password" name="loginPass">
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
              <label for="registerUserName" class="form-label">Username</label>
              <input type="text" class="form-control register-validar" id="registerUserName" placeholder="Username" name="registerUser">
            </div>
            <div class="mb-3">
              <label for="registerCapital" class="form-label">Capital Inicial</label>
              <input type="number" class="form-control register-validar" id="registerCapital" placeholder="Capital" name="registerCap">
            </div>
            <div class="mb-3">
              <label for="registerPassword" class="form-label">Password</label>
              <input type="password" class="form-control register-validar" id="registerPassword" placeholder="Password" name="registerPass">
            </div>
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Confirmar Password</label>
              <input type="password" class="form-control register-validar" id="confirmPassword" placeholder="Confirmar Password" name="confirmPass">
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

  <!--Modal Mes-->
  <div class="modal fade" id="modalBank" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
    <div class="modal-dialog modal-sm modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h6>Modificar Capital</h6>
          <button class="close" type="button" data-bs-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">x</span>
          </button>
        </div>
        <div class="modal-body">
          <form>
            <div class="row">
              <div class="col-sm-12">                
                <div class="form-row">
                  <div class="form-group col-sm-12">
                    <label for="txtNuevoCapital">Nuevo Capital</label>
                    <input type="number" class="form-control form-control-sm" id="txtNuevoCapital" name="nuevoCapital">
                  </div>
                </div>
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary btn-sm" type="button" id="btnActualizarBank">Actualizar</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Bootstrap 5 JS Bundle -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
  <c:if test="${not empty Script}">
    <script src="${pageContext.request.contextPath}/resources/js/${Script}"></script>
  </c:if>

</body>

</html>