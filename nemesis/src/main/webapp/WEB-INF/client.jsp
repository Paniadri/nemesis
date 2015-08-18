<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<title>VM</title>
</head>
<body>

        <script type="text/javascript"
            src="guacamole-common-js/all.min.js"></script>

		<h3>Máquina</h3>

		<!-- Display -->
        <div id="display"></div>
        <!-- Init -->
        <script type="text/javascript"> /* <![CDATA[ */
	   
		// Get display div from document
       var display = document.getElementById("display");
        
    	// Instantiate client, using an HTTP tunnel for communications.
       var guac = new Guacamole.Client(
           new Guacamole.HTTPTunnel("tunnel")
       );
    	
   		// Add client to display div
       display.appendChild(guac.getDisplay().getElement());
    
   		//ver a que nos conectamos
       var data= "direccion=" + "${direccion}"
       + "&puerto=" + "${puerto}"
       // Connect client
       guac.connect(data);	
   	
    	// Error handler
       guac.onerror = function(error) {
           alert(error);
       };
   		
   		 // Disconnect on close
       window.onunload = function() {
           guac.disconnect();
       }
   		 
    	// Mouse
       var mouse = new Guacamole.Mouse(guac.getDisplay().getElement());
       mouse.onmousedown = 
       mouse.onmouseup   =
       mouse.onmousemove = function(mouseState) {
           guac.sendMouseState(mouseState);
       };
       // Keyboard
       var keyboard = new Guacamole.Keyboard(document);
       keyboard.onkeydown = function (keysym) {
           guac.sendKeyEvent(1, keysym);
       };
       keyboard.onkeyup = function (keysym) {
           guac.sendKeyEvent(0, keysym);
       };
		
        /* ]]> */ </script>


</body>
</html>