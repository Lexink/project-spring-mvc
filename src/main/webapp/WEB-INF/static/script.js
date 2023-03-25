$(document).ajaxStop(function(){
    window.location.reload();
});
function upTo(el, tagName) {
    tagName = tagName.toLowerCase();

    while (el && el.parentNode) {
        el = el.parentNode;
        if (el.tagName && el.tagName.toLowerCase() === tagName) {
            return el;
        }
    }
    return null;
}
function createSelector(options, selectedValue) {
    const sel = document.createElement('select');

    sel.innerHTML = options.map(option =>{
        const selected = option===selectedValue?" selected":"";
        return `<option value="${option}" ${selected}>${option}</option>`;
    });

    return sel;
}

function createInput(currentValue){
    const input = document.createElement('input');
    input.type = "text";
    input.value = currentValue;
    return input;
}
function replaceElement(cell, tag, value, options){
    switch(tag){
        case "input": {
            cell.innerHTML="";
            const input = createInput(value)
            cell.appendChild(input);
        }
            break;
        case "selector": {
            cell.innerHTML="";
            const selector = createSelector(options, value)
            cell.appendChild(selector);
        }
            break;
    }
}

const buttons = document.querySelectorAll('button')
buttons.forEach((button)=> {
    button.addEventListener("click", (eventu)=> {
        const {target} = eventu;
        if (button.className === "is-edit"){
            edit(eventu, target)
        } else if (button.className === "is-save"){
            update(eventu, target);
        } else if (button.className === "is-delete") {
            deleteTask(eventu, target);
        }
    });
});

function edit(event, target){
    event.preventDefault();
    event.stopPropagation();

    let trElement = upTo(target, "tr");

    trElement.querySelectorAll("td").forEach((item)=> {
        const name = item.getAttribute("data-name");
        const text = item.innerHTML;

        switch (name){
            case "description": replaceElement(item, "input", text);
                break;
            case "status": replaceElement(item, "selector", text, statusNames);
                break;
        }
    });

    trElement.className = `is-edit`;
}

function update(event, target){
    event.preventDefault();
    event.stopPropagation();
    let trElement = upTo(target, "tr");
    let id = trElement.getAttribute("data-id");
    let updatedParameters = {};
    updatedParameters.id = id;
    updatedParameters.description = "";

    trElement.querySelectorAll("td").forEach((item)=> {
            name = item.getAttribute("data-name");
            switch (name) {
                case "description":
                    updatedParameters.description = item.childNodes[0].value;
                    break;
                case "status":
                    updatedParameters.status = item.childNodes[0].value;
                    break;
            }
    })
    console.log(updatedParameters);

    if (updatedParameters.description === "" || updatedParameters.status === ""){
        alert("Field \"description\" required and can't be empty!")
        trElement.className="is-edit";
    } else {
        trElement.className = `is-save`;
        $.ajax( {
            type: "POST",
            async: false,
            url: getMainURL() + `/`,
            dataType: 'json',
            data: JSON.stringify(updatedParameters),
            contentType: "application/json; charset=utf-8",
        });
    }
}

function deleteTask(event, target){

    event.preventDefault();
    event.stopPropagation();
    let trElement = upTo(target, "tr");
    let id = trElement.getAttribute("data-id");

    $.ajax({
        url: getMainURL() + `/${id}`,
        type: 'DELETE',
    });
}
function getMainURL() {
    let path = window.location.href;
    let endPosition = path.indexOf('?');

    if (endPosition > 0){
        return path.substring(0, endPosition)
    }
    return path;
}