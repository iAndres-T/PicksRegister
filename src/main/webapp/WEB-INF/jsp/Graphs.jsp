<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <div id="graphsContainer"></div>

    <script>
      // basePath para AJAX
      window.basePath = "${pageContext.request.contextPath}";
      // Exportar listas como JSON simple para Graphs.js
      window.Casinos = [
        <c:forEach var="c" items="${Casinos}" varStatus="st">
          {id: ${c.id}, name: "${c.name}"}<c:if test="${!st.last}">,</c:if>
        </c:forEach>
      ];

      window.Sports = [
        <c:forEach var="s" items="${Sports}" varStatus="st2">
          {id: ${s.id}, name: "${s.name}"}<c:if test="${!st2.last}">,</c:if>
        </c:forEach>
      ];
    </script>

    <!-- Incluir Chart.js desde CDN -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>