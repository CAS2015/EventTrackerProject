window.addEventListener('load',function(evt){
    console.log('script.js loaded');
    init();
});

function init() {
    loadUsers();
};

function loadUsers() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET','api/users');

    xhr.onreadystatechange = function () {
        if(xhr.readyState === 4) {
            if(xhr.status === 200) {
                let users = JSON.parse(xhr.responseText);
                displayUsers(users);
            }
            else {
                displayError('Error retrieving users: ' + xhr.status);
            }
        }
    };

    xhr.send();
};

function displayError(msg) {
    //TODO
    console.error(msg);
    let div = document.getElementById('errors');
    let element = document.createElement('h3');
    element.textContent = msg;
    div.appendChild(element);
    
};

function displayUsers(users) {
    let div = document.getElementById('userTable');
    //TODO: Make a beautiful table
    //TODO: Add click events for all controller methods
    for (const user of users) {
        let li = document.createElement('li');
        li.textContent = user.firstName;
        div.appendChild(li);
    }

};