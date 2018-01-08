document.getElementById('zonRech').style.visibility = "hidden";
document.getElementById('zonRechSub').style.visibility = "hidden";

// Get the modal
var modal = document.getElementById('myModal');

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
	modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
	modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target == modal) {
		modal.style.display = "none";
	}
}

var requete;
//function AJAX envoie requete
function rechercheDonnee(){
	var donnees = document.getElementById("donnees");
	var url = "search?valeur=" +escape(donnees.value);
	if (window.XMLHttpRequest){
		requete = new XMLHttpRequest();
		requete.open("GET",url,true);
		requete.onreadystatechange = majIHM;
		requete.send(null);
	}else if (window.ActiveXObject){
		requete = new ActiveXObject("Microsoft.XMLHTTP");
		if(requete){
			requete.open("GET",url,true);
			requete.onreadystatechange = majIHM;
			requete.send();
		}
		}else{
			alert("le navigateur ne supporte pas cette techno")
		
	}
}
// function AJAX retour requete
function majIHM(){
	
	if (requete.readyState == 4) {
		
		if (requete.status == 200) {
			console.log(requete.responseText)
			var reponse = JSON.parse(requete.responseText);
			var select = document.getElementById('tableSub');
			if (reponse.length !=0){
				document.getElementById('iTable').style.visibility ='hidden';
				select.innerHTML="";
			for (var i = 0; i <reponse.length; i++){
			      var opt = document.createElement('tr');
			      var tdLastName = document.createElement('td');
			      var tdFirstName =   document.createElement('td');
			      var tdEmprunt   = document.createElement('td');
			      var tdRadioButton = document.createElement('td');
			      tdRadioButton.setAttribute('class','tdButton');
  			      var radioInput = document.createElement('input');
			      radioInput.setAttribute('type','radio');
			      radioInput.setAttribute('name','choixSub');
			      radioInput.setAttribute('value',reponse[i].id);
			      radioInput.setAttribute('onchange','getId('+reponse[i].id+');highLight(this)');
			         tdRadioButton.appendChild(radioInput);
			      opt.appendChild(tdRadioButton);
			      opt.appendChild(tdLastName);
			      tdLastName.innerHTML = reponse[i].lastName;
		      opt.appendChild(tdFirstName);
		      tdFirstName.innerHTML = reponse[i].fisrtName;
		      opt.appendChild(tdEmprunt);
		      if (reponse[i].nbrEmprunt>0){
		      tdEmprunt.innerHTML = "emprunt en cours";
		      }else{
		    	  tdEmprunt.innerHTML = "pas d'emprunt";
		      }
			      opt.value = reponse[i].id;
				 select.appendChild(opt);
			}
			}else{
				document.getElementById('iTable').style.visibility ='visible';
			}
		} else {
		alert('Une erreur est survenue lors de la mise à jour de la page.'+
		'\n\nCode retour = '+requete.statusText);   
		}
	}
}
function choixAdd(n) {
	if (n == 1) {
		document.getElementById('txtCibl7').value = "sub";
	} else {
		document.getElementById('txtCibl7').value = "book";
	}
	document.add.submit();

}

function rechAuteur() {
	var btnBoxAuteur = document.getElementById('boxA');
	var txtBox = document.getElementById("boxA").value;
	boxG.value = "";
	txtTitre.value = "";

}
function rechAuteurTexUp() {
	document.getElementById('boxA').value = "";
}
function popUpInfo2() {
	var popup = document.getElementById("myPopup2");
	popup.classList.toggle("show");
}
function rechAuteurUp() {
	document.getElementById('namAut').value = "";
	document.getElementById('prenAut').value = "";
	document.getElementById('agAut').value = "";
}
function modeBook() {
	document.getElementById('zonRech').style.visibility = "visible";
	document.getElementById('zonRechSub').style.visibility = "hidden";
}
function modeSub() {
	document.getElementById('zonRech').style.visibility = "hidden";
	document.getElementById('zonRechSub').style.visibility = "visible";
}

//AJAX rechTitle
var xhr_object = null;
function rechTitre() {
	boxG.value = "";
	boxA.value = "";
	var donneesTitre = document.getElementById("txtTitre");
	var urlT = "Acceuil/searchTitle?valeur=" +escape(donneesTitre.value);
	if (window.XMLHttpRequest) // Firefox
	{
		xhr_object = new XMLHttpRequest();
		xhr_object.open("GET",urlT,true);
		xhr_object.onreadystatechange = majTitle;
		xhr_object.send(null);
	}else if (window.ActiveXObject) // Internet explorer
	{
		xhr_object = new ActiveXObject("Microsoft.XMLHTTP");
		if(xhr_object){
			xhr_object.open("GET",urlT,true);
			xhr_object.onreadystatechange = majIHMTitle;
			xhr_object.send();
		}
		}else{
			alert("le navigateur ne supporte pas cette techno")
		
	}
}
function majTitle(){
if (xhr_object.readyState == 4) {
	if (xhr_object.status == 200) {
		console.log(xhr_object.responseText);
		var reponse = JSON.parse(xhr_object.responseText);
		console.log(reponse);
		var tableBook = document.getElementById('bookTable');
		if (reponse.length !=0){
			document.getElementById('TableBook').style.visibility ='hidden';
			tableBook.innerHTML="";
		for (var i = 0; i < reponse.length; i++){
		      var rowT = document.createElement('tr');
		      var tdTitle = document.createElement('td');
		      var tdSubTitle =   document.createElement('td');
		      var tdAuthor   = document.createElement('td');
		      var tdGenre = document.createElement('td');
		      var tdRadioButton = document.createElement('td');
		      tdRadioButton.setAttribute('class','tdButton2');
		    
			      var radioInput = document.createElement('input');
		      radioInput.setAttribute('type','radio');
		      radioInput.setAttribute('name','choixBook');
		      radioInput.setAttribute('value',reponse[i].isbn);
		      radioInput.setAttribute('onchange','getIsbn('+reponse[i].isbn+');highLight2(this)');
		         tdRadioButton.appendChild(radioInput);
		         rowT.appendChild(tdRadioButton);
		         rowT.appendChild(tdTitle);
		      tdTitle.innerHTML = reponse[i].title;
		      rowT.appendChild(tdSubTitle);
	      tdSubTitle.innerHTML = reponse[i].subtitle;
	      rowT.appendChild(tdSubTitle);
	      tdAuthor.innerHTML = reponse[i].author.lastName;
	      rowT.appendChild(tdAuthor);
	      tdGenre.innerHTML = reponse[i].genre;
	      rowT.appendChild(tdGenre);
	      rowT.value = reponse[i].isbn;
	      tableBook.appendChild(rowT);
		}
		}else{
			document.getElementById('iTableBook').style.visibility ='visible';
		}
	} else {
	alert('Une erreur est survenue lors de la mise à jour de la page.'+
	'\n\nCode retour = '+ xhr_object.statusText);   
	}
}
}

function highLight(e){
	var x = document.getElementsByClassName("tdButton");
	// reinitialise affichage
	for( var i=0; i<x.length;i++){
	x[i].style.backgroundColor = "lightYellow";
	}
	// Get parent of e
	var parent = e.parentNode;
	// Change css style of parent of e
	parent.style.backgroundColor = "lightBlue";
}
function highLight2(f){
	var x = document.getElementsByClassName("tdButton2");
	// reinitialise affichage
	for( var i=0; i<x.length;i++){
	x[i].style.backgroundColor = "lightYellow";
	}
	// Get parent of f
	var parent = f.parentNode;
	// Change css style of parent of e
	parent.style.backgroundColor = "lightBlue";
}
function rechGenre() {
	boxA.value = "";
	txtTitre.value = "";
}
function getIsbn(isbn) {
	document.getElementById('txtCibl1').value = isbn;
	document.getElementById('txtCibl3').value = isbn;
	document.getElementById('txtCibl5').value = isbn;
}

function getId(id) {
	document.getElementById('txtCibl2').value = id;
	document.getElementById('txtCibl4').value = id;
	document.getElementById('txtCibl6').value = id;
}
function getIdCopy(nb) {
	document.getElementById('txtCibl1').value = nb;
	document.getElementById('txtCibl5').value = nb;
	document.getElementById('txtCibl7').value = nb;
	document.getElementById('txtCibl10').value = nb;
	document.getElementById('txtCibl13').value = nb;
}