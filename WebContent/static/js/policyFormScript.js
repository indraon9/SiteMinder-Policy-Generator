function addDirTextField(element){
	try{
		var appendChild = document.getElementById("userDirList").lastChild.cloneNode(true);
		document.getElementById("userDirList").appendChild(appendChild);
	}catch(e){
		alert(e);
	};

};

function deleteDirTextField(element){
	try{
		//alert(element.parentNode.parentNode.parentNode.parentNode);
		//alert(document.getElementById("userDirList").childNodes.length);
		if(document.getElementById("userDirList").childNodes.length == 2){
			alert("This is wrong");
			return;
		}else
		document.getElementById("userDirList").removeChild(element.parentNode.parentNode.parentNode);
	}catch(e){
		alert(e);
	};

};


function addRealmRow(element){
	try{
		var appendChild = element.parentNode.parentNode.cloneNode(true);
		element.parentNode.parentNode.parentNode.appendChild(appendChild);
	}catch(e){
		alert(e);
	};

};

function deleteRealmRow(element){
	try{
		if(element.parentNode.parentNode.parentNode.childNodes.length == 2){
			alert("This is wrong");
			return;
		}else
		element.parentNode.parentNode.parentNode.removeChild(element.parentNode.parentNode);
	}catch(e){
		alert(e);
	};

};


