<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>RSA cryptography demonstration using Java BigInteger class</title>

<link rel="stylesheet"
              href="<c:url value="/resources/stylesheet/rsaDemo.css" />" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<script>

"use strict";


var Debugger = function() { };// create  object
  Debugger.log = function(message) {
  try {
    console.log(message);
  } catch(exception) {
    return;
  }
}


function eventWindowLoaded() {
 
	function dKeyGen() { 
		console.log("dKeyGen begin");
		var answer = null;

		var p = $("#dKeyGenForm input[name='p']").val();
		var q = $("#dKeyGenForm input[name='q']").val();
		var e = $("#dKeyGenForm input[name='e']").val();
		    
		// validity check
	    var isnump = /\d+$/.test(p);
	    var isnumq = /\d+$/.test(q);
	    var isnume = /\d+$/.test(e);
	    var isZerop = /^0+$/.test(p);
	    var isZeroq = /^0+$/.test(q);
	    var isZeroe = /^0+$/.test(e);

	    if (!(isnump && isnumq && isnume) || isZerop || isZeroq || isZeroe) {
	      	alert("invalid input");
	      	return;
	    }
	       
		var message = {"p" : p, "q" : q, "e" : e};// initialize key
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/dKeyGen" />',
			data : JSON.stringify(message),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS");
				
				var status = data["status"];
				if (status == "NOT_COPRIME") {
					alert("e and lambda not coprime");
					return;
				}
				if (status != "OK") {
					alert("error");
					return;
				}
				
				var n = data["n"];
				var d = data["d"];
		        
				$('#pkeyDisp').text(p);
				$('#qkeyDisp').text(q);
				$('#nkeyDisp').text(n);			        
				$('#ekeyDisp').text(e);			        
				$('#dkeyDisp').text(d);
		
				$('#ee').text(e);
				$('#en').text(n);
				$('#dd').text(d);
				$('#dn').text(n);
			},
			
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
		    
		console.log("dKeyGen completed"); 
	}// dKeyGen
	
	function encrypt() {
	   
		var answer = null;
	    var status;
	    
	    console.log("encrypt begin");
	    
	    var msg = $("#encryptForm input[name='msg']").val();
	    
	 	// validity check
	    var isnummsg = /\d+$/.test(msg);
	  
	    if (!isnummsg) {
	    	alert("invalid input");
	      	return;  
	    }
	    
	    var message = {"type" : "ENCRYPT", "message" : msg};
	    var msgE;
	    
	    $.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/rsaProcess" />',
			data : JSON.stringify(message),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS");
				
				var type = data["type"];
				
				if (type == "TOOLONG") {
					alert("message is too long"); 
					return;
				}
				if (type != "ENCRYPTED") {
					alert("type error");
					return;
				}
				
				// normal case
				msgE = data["message"];
				console.log("msgE: " + msgE);
					
				$("#msgeDisp").text(msg);
		        $("#msgEeDisp").text(msgE);
				
			},
			
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	    
	    console.log("msg: " + msg);
	    
	    console.log("encrypt completed");
	}// encrypt
	

  
	function decrypt() {
		
		var answer = null;

    	var msgE = $("#decryptForm input[name='msgE']").val();
    
    	var isnummsgE = /\d+$/.test(msgE);
    
    	console.log("msgE: " + msgE)
    
     	if (!isnummsgE) {
      		alert("invalid input");
      		return;      
    	}
    
    	var msgD;
    
    	var message = {"type" : "DECRYPT", "message" : msgE};
    
    	$.ajax({
			type : "POST",
			contentType : "application/json",
			url : '<c:url value="/rsaProcess" />',
			data : JSON.stringify(message),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS");
			
				var type = data["type"];
			
				if (type == "TOOLONG") {
					alert("message is too long"); 
					return;
				}
				if (type != "DECRYPTED") {
					alert("type error");
					return;
				}
			
				// normal case
				msgD = data["message"];
				console.log("msgD: " + msgD);
				
	        	$("#msgEdDisp").text(msgE);
            	$("#msgDdDisp").text(msgD);
			
			},
		
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
      
  	}// decrypt
	
  	$("#dKeyGenForm").submit(function(event) { dKeyGen(); return false; });

  	$("#encryptForm").submit(function(event) { encrypt(); return false; });
  
 	$("#decryptForm").submit(function(event) { decrypt(); return false; });
  
  	console.log("eventWindowLoaded completed");
  
}// eventWindowLoaded

$(document).ready(eventWindowLoaded);

</script>

</head>

<body>

  <nav>
  <br>
  <br>
  </nav>
  
  <div id="intro">
  <h1>RSA cryptography demonstration</h1>
  <p>I present here a demonstration of an RSA algorithm implemented by the Java BigInteger class. The communication is AJAX based. You can interactively test the basic RSA operations:</p>
  <ul>
    <li>decryption (private) key generation</li>
    <li>message encryption</li>
    <li>message decryption</li>
  </ul>
  <br>
  </div>

  <div id="dKeyGen">
  <h3>Decryption key generation</h3>
  <form name="deKeyGenForm" id="dKeyGenForm">
     p: <input type="text" name="p" size="20"><br>
     q: <input type="text" name="q" size="20"><br>
     e: <input type="text" name="e" size="20"><br>
     Compute d such as d * e = 1 (mod lambda(p*q))<br>
     where p and q are two different primes and<br>
     lambda(p*q) = LCM(p-1, q-1).<br>
     Also compute n = p * q.
     <input type="submit" name="dKeyGen-btn" value="dKeyGen">
  </form>
  <p>p: <span id="pkeyDisp"></span><br>
  q: <span id="qkeyDisp"></span><br>
  e: <span id="ekeyDisp"></span><br>
  n: <span id="nkeyDisp"></span><br>
  d: <span id="dkeyDisp"></span>
  </p>
  <br>
  </div>
 

  <div id="encryption">
  <h3>Encryption</h3>
  <p>You can enter an integer in the msg field.<br>
  It is then encrypted using the public encryption key (e, n) and displayed in msgE field.<br>
  Note that msg should satisfy msg &lt; n.<br>
  If this condition is not satisfied an error message is displayed</p>
  e: <span id="ee"></span><br/>
  n: <span id="en"></span>
  <form name="encryptForm" id="encryptForm">
     msg: <input type="text" id="msg" name="msg" size="20"><br>
     Encrypt msg using encryption (public) key (e, n)
     <input type="submit" name="encrypt-btn" value="encrypt">
  </form>
  <p>msg: <span id="msgeDisp"></span><br>
  msgE: <span id="msgEeDisp"></span></p>
  <br>
  </div>  

  <div id="decryption">
  <h3>Decryption</h3>
  <p>You can enter an integer in the msgE field. It is then decrypted using the private decryption key (d, n) and displayed in msgD field.<br>
  If you copy and paste the result of the encryption, then the original msg is obtained.<br>
  <br> 
  Note that msgE should satisfy msgE &lt; n.<br>  
  If this condition is not satisfied an error message is displayed.<br></p>
  d: <span id="dd"></span><br/>
  n: <span id="dn"></span>
  <form name="decryptForm" id="decryptForm">
     msgE: <input type="text" name="msgE" size="20"><br>
     Decrypt msgE using decryption (private) key (d, n) 
     <input type="submit" name="decrypt-btn" value="decrypt">
  </form>
  <p>msgE: <span id="msgEdDisp"></span><br>
  msgD: <span id="msgDdDisp"></span>
  </p>
  <br>
  </div>  

  <div id="annex">
  <h3>A Prime Number Sample List</h3>
  <p>I give in annex a short list of probably prime numbers that I obtained using my own     implementation of the Miller-Rabin primality test. Each number was tested for 100000 random witnesses.</p>
  <ul>
  <li>36064141637732512937</li>
  <li>94512646076867083291</li>
  <li>32215756043207460811</li>
  <li>88790681482514479051</li>
  <li>65344767568804500031</li>
  <li>81878498602376012071</li>
  <li>56656466634215454073</li>
  <li>78499563759584438983</li>
  <li>28276182874772515261</li>
  <li>80880511004313708053</li>
  <li>20488067719728589759</li>
  </ul>
  <br><br>
  </div>



<footer>
<p>Dominique Ubersfeld, Cachan</p>

</footer>

</body>
</html>
