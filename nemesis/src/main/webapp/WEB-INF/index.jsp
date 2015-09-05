<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>VMs</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
</head>
<body>

    <div class="panel panel-primary">
	  <!-- Default panel contents -->
	  <div class="panel-heading">
		  <h4 style="display:inline">VMs disponibles</h4>
		  <button style="display:inline; margin-left: 20px" onclick="window.location='/nemesis/'"  class="btn btn-default">Actualizar Tabla</button>
	  </div>

	  <table class="table table-striped">
	    <thead>
		  <tr>
		     <th>ID</th>
		     <th>Nombre</th>
		     <th>Status</th>
		     <th>Puerto</th>
		     <th>Hostname</th>
		  </tr>
		 </thead>
		 <tbody>
	        <c:forEach var="vm" items="${vms}">
	        	<tr>
		            <td>${vm.id}</td>
		            <td>${vm.name}</td>
		            <td>${vm.status}</td>
		            <td>${vm.port}</td>
		            <td>${vm.host}</td>
	            </tr>
	        </c:forEach>
	     </tbody>
	  </table>
	</div>
	
	
	 <div class="panel panel-default">
	  <div class="panel-heading">Nuevo escenario</div>
	  <div class="panel-body">
		  <form:form class="form-inline" method="post" action="add" commandName="escenario">
	    	<div class="form-group">
			  <form:radiobutton path="numeroEscenario" value="1"/> 1
			  <form:radiobutton path="numeroEscenario" value="2"/> 2
		 	</div>
		 	
		    <div class="form-group">
			    <button type="submit" type="submit" class="btn btn-primary">Crear</button>
		    </div>
	 	 </form:form>   
	  </div>
	</div>
	
	<div class="panel panel-default">
	  <div class="panel-heading">Eliminar escenario</div>
	  <div class="panel-body">
		<form:form class="form-inline" method="post" action="delete" commandName="vm">
			<div class="form-group">
				<label for="id">ID </label>
		    	<form:select  items="${vms}" path="id" itemLabel="vm.id" itemValue="vm.id"/>
		    </div>
		     <div class="form-group">
			    <button type="submit" type="submit" class="btn btn-primary">Eliminar</button>
		    </div>
		</form:form>
	  </div>
	</div>
	
	<div class="panel panel-info">
		<div class="panel-heading">Conexión</div>
		 <form class="form-inline">
		  <div class="form-group">
		    <label for="direccion">Direccion</label>
	   		<input type="text" class="form-control" id="direccion" placeholder="Dirección IP">
		  </div>
		  
		  <div class="form-group">
		    <label for="puerto">Puerto</label>
		      <input type="text" class="form-control" id="puerto" placeholder="Puerto VNC">
		  </div>
	
		  <div class="form-group">
		      <button class="btn btn-primary" name="conexion" id="conexion">Conectar</button>
		  </div>
		</form>
	</div>
	
    
    <script type="text/javascript"> /* <![CDATA[ */
	   
       conexion.onclick = function() {
    	   
    	   var direccion = document.getElementById("direccion");
           var puerto = document.getElementById("puerto");
           var data =
                  "direccion=" + encodeURIComponent(direccion.value)
               + "&puerto=" + encodeURIComponent(puerto.value)
    	   
    	   window.open("mostrar?" + data);
       }
       
        /* ]]> */ </script>
    
    
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>