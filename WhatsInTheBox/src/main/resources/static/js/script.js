window.addEventListener('load',function(evt){
    console.log('script.js loaded');
    init();
});

function init() {
    let button = document.getElementById('login');
    button.addEventListener('click', function(evt){
        loadUsers(1);//TODO change this once there is a sign in
    })
};

function loadUsers(userId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET',`api/users/${userId}`);

    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 200) {
                let user = JSON.parse(xhr.responseText);
                // let nextURL = `/api/users/${userId}`;
                // let nextState = { additionalInformation: 'Updated the URL with JS' };

                // // This will create a new entry in the browser's history, without reloading
                // window.history.pushState(nextState,"What's In The Box", nextURL);
                displayUsers(user);
            }
            else {
                displayError('Error retrieving user: ' + xhr.status);
            }
        }
    };

    xhr.send();
};

function displayError(msg) {
    console.error(msg);
    let div = document.getElementById('errors');
    let element = document.createElement('h3');
    element.textContent = msg;
    div.appendChild(element);
    
};

function displayUsers(user) {
    let div = document.getElementById('homepage');
    div.textContent = '';
    div = document.getElementById('header');
    let element = document.createElement('h3');
    element.textContent = `Welcome, ${user.firstName}!`
    div.appendChild(element);

    loadLocations(user.id);

};

function loadLocations(userId) {
    let xhr = new XMLHttpRequest();
    //xhr.open('GET',`./${userId}/locations`);
    xhr.open('GET', `api/users/${userId}/locations`)

    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 200) {
                let locations = JSON.parse(xhr.responseText);
                // let nextURL = `./${userId}/locations`;
                // let nextState = { additionalInformation: 'Updated the URL with JS' };

                // // This will create a new entry in the browser's history, without reloading
                // window.history.pushState(nextState,"What's In The Box", nextURL);
                displayLocations(userId, locations);
            }
            else {
                displayError('Error retrieving locations: ' + xhr.status);
            }
        }
    };

    xhr.send();
};

function displayLocations(userId, locations) {
    let div = document.getElementById('locationsTable');
    div.textContent = '';

    div = document.getElementById('boxesTable')
    div.textContent = '';

    let element = document.createElement('h3');
    element.textContent = 'Your Locations: '
    div.appendChild(element);


    for (const location of locations) {
        element = document.createElement('button');
        element.textContent = location.name;
        element.addEventListener('click', function(evt){
            loadBoxes(userId, location);
        })
        div.appendChild(element);
    }
    
    element = document.createElement('button');
    element.textContent = 'Add New Location';
    element.addEventListener('click', function(evt){
        createLocation(userId);
    })
    div.appendChild(element);
};

function createLocation(userId) {
    
    let div = document.getElementById('locationsTable');
    div.textContent = '';
    div = document.getElementById('locationFormDiv');

    let element = document.createElement('h3');
    element.textContent = 'Add New Location Form';
    div.appendChild(element);

    let form = document.createElement('form');
    form.name = 'addLocationForm';
    div.appendChild(form);

    element = document.createElement('label');
    element.for = 'name';
    element.textContent = 'Name: ';
    form.appendChild(element);

    element = document.createElement('input');
    element.type = 'text';
    element.name = 'name';
    element.placeholder = 'Denver Move';
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'type';
    element.textContent = 'Location Type: ';
    form.appendChild(element);

    element = document.createElement('input');
    element.type = 'text';
    element.name = 'type';
    element.placeholder = 'Move, Storage Unit, etc.';
    form.appendChild(element);

    element = document.createElement('button');
    element.textContent = 'Add Location';
    form.appendChild(element);
    element.addEventListener('click', function(evt){
        evt.preventDefault();
        submitLocation(userId);
    })
}

function submitLocation(userId) {
            
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `api/users/${userId}/locations`)

    xhr.setRequestHeader('Content-type', 'application/json');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 ) {
            if ( xhr.status === 201 || xhr.status === 200) {
                let newLocation = JSON.parse(xhr.responseText);

                loadBoxes(userId, newLocation);
            }
            else {
                console.log("POST request failed.");
                console.error(xhr.status + ': ' + xhr.responseText);
            }
        }
    }

    let form = document.addLocationForm;

    let location = {};

    location.name = form.name.value;
    location.type = form.type.value;

    let locationJson = JSON.stringify(location);
    xhr.send(locationJson);
}

function loadBoxes(userId, location) {

    let xhr = new XMLHttpRequest();
    // xhr.open('GET',`./locations/${locationId}/boxes`);
    xhr.open('GET', `api/users/${userId}/locations/${location.id}/boxes`)
    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 200) {
                let boxes = JSON.parse(xhr.responseText);
                // let nextURL = `./locations/${locationId}/boxes`;
                // let nextState = { additionalInformation: 'Updated the URL with JS' };

                // // This will create a new entry in the browser's history, without reloading
                // window.history.pushState(nextState,"What's In The Box", nextURL);
                displayBoxes(boxes, userId, location);
            }
            else {
                displayError('Error retrieving locations: ' + xhr.status);
            }
        }
    };

    xhr.send();
};

function displayBoxes(boxes, userId, location) {
    let div = document.getElementById('locationsTable');
    div.textContent = '';

    div = document.getElementById('boxDetails');
    div.textContent = '';

    div = document.getElementById('qrcode');
    div.textContent = '';

    div = document.getElementById('boxesTable');
    div.textContent = '';

    let element = document.createElement('h3');
    element.textContent = location.name + ' Boxes:';
    div.appendChild(element);

    element = document.createElement('h5');
    element.textContent = 'Total Number of Boxes: ' + boxes.length;
    div.appendChild(element);

    element = document.createElement('button');
    element.textContent = 'Delete Location'
    div.appendChild(element);
    element.addEventListener('click', function(e){
        deleteLocation(userId, location.id);
    })

    element = document.createElement('button');
    element.textContent = 'Add New Box To Location'
    div.appendChild(element);
    element.addEventListener('click', function(e){
        addBox(userId, location);
    })

    div = document.getElementById('locationFormDiv')
    div.textContent = '';

    div = document.getElementById('boxesTable');

    let table = document.createElement('table');

    let row = table.insertRow();
    let headers = ['Id', 'Name', 'Room', 'Image', 'Details'];
    for (let index = 0; index < headers.length; index++) {
        element = row.insertCell(index);
        element.textContent = headers[index];
    };
    
    for (const box of boxes) {
        row = table.insertRow();
        for (const key in box) {
            if (box.hasOwnProperty(key)) {
                if(key ==='id' || key ==='name' || key ==='room' || key ==='img1Url'){
                    element = row.insertCell();
                    element.textContent = box[key];
                }
            }
        }
        let button = document.createElement('button');
        button.textContent = 'Details';
        button.addEventListener('click', function(evt) {
            loadBoxDetails(userId, location, box);
        })
        element = row.insertCell();
        element.appendChild(button);

    };
    div.appendChild(table);
    
};

function deleteLocation(userId, locationId) {
    let xhr = new XMLHttpRequest();
    // xhr.open('DELETE',`./locations/${locationId}`);
    xhr.open('DELETE', `api/users/${userId}/locations/${locationId}`)
    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 204) {
                // let nextURL = `./locations/${locationId}`;
                // let nextState = { additionalInformation: 'Updated the URL with JS' };

                // // This will create a new entry in the browser's history, without reloading
                // window.history.pushState(nextState,"What's In The Box", nextURL);
                loadLocations(userId);
            }
            else {
                displayError('Error retrieving locations: ' + xhr.status);
            }
        }
    };

    xhr.send();
};

function loadBoxDetails(userId, location, box) {
    let div = document.getElementById('boxesTable');
    div.textContent = '';
    
    div = document.getElementById('boxFormDiv')
    div.textContent = '';

    div = document.getElementById('boxDetails');

    let element = document.createElement('h3');
    element.textContent = `${box.name} Details: `

    let table = document.createElement('table');

    generateQr(userId,location.id,box);
    for (const key in box) {
        if (box.hasOwnProperty(key)) {
            if(key ==='content' || key ==='id' || key ==='room' || key ==='img1Url' || key === 'img2Url' || key === 'fragile'){
                    row = table.insertRow();
                    element = row.insertCell();
                    element.textContent = key;
                    element = row.insertCell();
                    element.textContent = box[key];
            }
        }
    } 
        let button = document.createElement('button');
        button.textContent = 'Update Box';
        button.addEventListener('click', function(evt) {
            addBox(userId, location, box);
        })
        element.appendChild(button);

        let button2 = document.createElement('button');
        button2.textContent = 'Delete Box';
        button2.addEventListener('click', function(evt) {
            deleteBox(userId, location, box.id);
        })
        element.appendChild(button2);
    div.appendChild(table);
};

function addBox(userId, location, box) {
    let div = document.getElementById('boxesTable');
    div.textContent = '';

    div = document.getElementById('boxDetails');
    div.textContent = '';
    
    div = document.getElementById('qrcode');
    div.textContent = '';

    div = document.getElementById('boxFormDiv');

    let element = document.createElement('h3');
    element.textContent = typeof box === 'undefined' ? 'Add New Box Form' : 'Update Box Form';
    div.appendChild(element);

    let form = document.createElement('form');
    form.name = 'addBoxForm';
    div.appendChild(form);

    element = document.createElement('label');
    element.for = 'name';
    element.textContent = 'Name: ';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'text';
    element.name = 'name';
    if(typeof box === 'undefined') {
        element.placeholder = 'Living Rm Decorations';
    }
    else {
        element.value = box.name;
    }
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'room';
    element.textContent = 'Room: ';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'text';
    element.name = 'room';
    element.value = typeof box === 'undefined' ? '': box.room;
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'fragile';
    element.textContent = 'Fragile: ';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'radio';
    element.id = 'yes';
    element.value = 'true';
    element.name = 'fragile';
    if(typeof box != 'undefined'){
        element.checked = box.fragile === true ? 'checked': '';
    }
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'yes';
    element.textContent = 'Yes';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'radio';
    element.id = 'no';
    element.value = 'false';
    element.name = 'fragile';
    if(typeof box != 'undefined'){
        element.checked = box.fragile === false ? 'checked': '';
    }
    form.appendChild(element);

    element = document.createElement('label');
    element.for = 'no';
    element.textContent = 'No';
    form.appendChild(element);

    element = document.createElement('label');
    element.for = 'img1Url';
    element.textContent = 'Img 1 URL: ';
    form.appendChild(element);

    element = document.createElement('input');
    element.type = 'text';
    element.name = 'img1Url';
    element.value = typeof box === 'undefined' ? '': box.img1Url;
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'img2Url';
    element.textContent = 'Img 2 URL: ';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'text';
    element.name = 'img2Url';
    element.value = typeof box === 'undefined' ? '': box.img2Url;
    form.appendChild(element);
    
    element = document.createElement('label');
    element.for = 'content';
    element.textContent = 'Content Details: ';
    form.appendChild(element);
    
    element = document.createElement('input');
    element.type = 'text';
    element.name = 'content';
    element.value = typeof box === 'undefined' ? '': box.content;
    form.appendChild(element);
  
    if(typeof box === 'undefined') {
        element = document.createElement('button');
        element.textContent = 'Add Box';
        form.appendChild(element);
        element.addEventListener('click', function(evt){
            evt.preventDefault();
            submitBox(userId,location);
        })
    }
    else{
        element = document.createElement('button');
        element.textContent = 'Update Box';
        form.appendChild(element);
        element.addEventListener('click', function(evt){
            evt.preventDefault();
            updateBox(userId,location,box.id);
        })
    }
   

};

function submitBox(userId, location) {
    console.log(location);
    let xhr = new XMLHttpRequest();
    xhr.open('POST', `api/users/${userId}/locations/${location.id}/boxes`)

    xhr.setRequestHeader('Content-type', 'application/json');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 ) {
            if ( xhr.status === 201 || xhr.status === 200) {
                let newBox = JSON.parse(xhr.responseText);
                loadBoxDetails(userId, location, newBox);
            }
            else {
                console.log("POST request failed.");
                console.error(xhr.status + ': ' + xhr.responseText);
            }
        }
    }

    let form = document.addBoxForm;

    let box = {};

    box.name = form.name.value;
    box.room = form.room.value;
    box.content = form.content.value;
    box.fragile = form.fragile.value;
    box.img1Url = form.img1Url.value;
    box.img2Url = form.img2Url.value;


    let boxJson = JSON.stringify(box);
    xhr.send(boxJson);

}

function generateQr(userId, locationId, newBox) {


    let qrcode = new QRCode(document.getElementById("qrcode"), {
        text: `http://localhost:8084/api/users/${userId}/locations/${locationId}/boxes/${newBox.id}`, //later "https://whatsinthebox.com/${uuid}"
        width: 128,
        height: 128,
        colorDark : "#000000",
        colorLight : "#ffffff",
        correctLevel : QRCode.CorrectLevel.H
    });
    
}

function updateBox(userId,location,boxId){
    let xhr = new XMLHttpRequest();
    xhr.open('PUT', `api/users/${userId}/locations/${location.id}/boxes/${boxId}`)
    xhr.setRequestHeader('Content-type', 'application/json');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 ) {
            if ( xhr.status === 201 || xhr.status === 200) {
                let newBox = JSON.parse(xhr.responseText);
 
                loadBoxDetails(userId, location, newBox);
            }
            else {
                console.log("POST request failed.");
                console.error(xhr.status + ': ' + xhr.responseText);
            }
        }
    }

    let form = document.addBoxForm;

    let box = {};

    box.name = form.name.value;
    box.room = form.room.value;
    box.content = form.content.value;
    box.fragile = form.fragile.value;
    box.img1Url = form.img1Url.value;
    box.img2Url = form.img2Url.value;

    let boxJson = JSON.stringify(box);
    xhr.send(boxJson);
}

function deleteBox(userId, location, boxId) {
    let xhr = new XMLHttpRequest();
    // xhr.open('DELETE',`./locations/${locationId}`);
    xhr.open('DELETE', `api/users/${userId}/locations/${location.id}/boxes/${boxId}`)
    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 204) {
                // let nextURL = `./locations/${locationId}`;
                // let nextState = { additionalInformation: 'Updated the URL with JS' };

                // // This will create a new entry in the browser's history, without reloading
                // window.history.pushState(nextState,"What's In The Box", nextURL);
                loadBoxes(userId,location);
            }
            else {
                displayError('Error deleting box: ' + xhr.status);
            }
        }
    };

    xhr.send();
};