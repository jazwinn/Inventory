let popupRow = null;

function DeleteItem() {
    if(popupRow != null){
        const row = popupRow;
        const id = row.querySelector("td").innerText; // assumes first column is ID

        fetch(`/inventory/${id}`, {method: 'DELETE' }).
        then(response => {
            if (response.ok) {
                row.remove(); // remove row from table
            } else {
                alert("Failed to delete item.");
            }
        }).catch(error => console.error("Error:", error));
    }

    CloseDeletePopup();
}

function OpenPopup(mode, button = null){
    document.getElementById("popup").style.display = "block";

    const addButton = document.getElementById("addButton");
    const saveButton = document.getElementById("saveButton");
    const header = document.getElementById("popup-header");

    if(mode == "add"){
        header.textContent = "Add Item";
        addButton.style.display = "block";
        saveButton.style.display = "none";
    }
    else if(mode == "edit"){
        if(button != null){
            popupRow = button.closest("tr");
            const id = popupRow.querySelector("td").innerText;
            RetrieveItemInfo(id);
        }

        header.textContent = "Edit Item";
        addButton.style.display = "none";
        saveButton.style.display = "block";
    }
}

function OpenDeletePopup(button){
    popupRow = button.closest("tr");

    document.getElementById("popup-delete-confirmation").style.display = "block";
}

function ClosePopup(){
    document.getElementById("popup").style.display = "none";

    //reset fields
    document.getElementById("name").value = "";
    document.getElementById("quantity").value = "";
    document.getElementById("price").value = "";
    document.getElementById("description").value = "";
}

function CloseDeletePopup() {
    document.getElementById("popup-delete-confirmation").style.display = "none";
}

function AddItem(){

    let itemName = document.getElementById("name").value;
    if(itemName.length > 0){
        const item = {
            name: itemName,
            quantity: parseInt(document.getElementById("quantity").value),
            price: parseFloat(document.getElementById("price").value),
            description: document.getElementById("description").value
        };// converts obhect to json

        fetch("/inventory", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item)
        })
            .then(response => {
                if (response.ok) {
                    alert("Item added!");
                    ClosePopup();
                    location.reload(); // reload the page to show new item
                } else {
                    alert("Failed to add item.");
                }
            })
            .catch(error => console.error("Error:", error));
    }
    else {
        alert("Missing required fields.");
    }
}

function UpdateItem(){

    let itemName = document.getElementById("name").value;
    if(itemName.length > 0 && popupRow != null){
        const item = {
            name: itemName,
            quantity: parseInt(document.getElementById("quantity").value),
            price: parseFloat(document.getElementById("price").value),
            description: document.getElementById("description").value
        };// converts obhect to json

        const id = popupRow.querySelector("td").innerText; // assumes first column is ID

        fetch(`/inventory/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(item)
        })
            .then(response => {
                if (response.ok) {
                    alert("Item Updated!");
                    ClosePopup();
                    location.reload(); // reload the page to show new item
                } else {
                    alert("Failed to add item.");
                }
            })
            .catch(error => console.error("Error:", error));
    }
    else {
        alert("Missing required fields.");
    }

}

function RetrieveItemInfo(id){
    fetch(`/inventory/${id}`).
    then(response => {
        if (!response.ok) {
            alert("id does not exist item.");
        }
        return response.json();
    })
    .then(item => {
        document.getElementById('name').value = item.name;
        document.getElementById('quantity').value = item.quantity;
        document.getElementById('price').value = item.price;
        document.getElementById('description').value = item.description;


    }).catch(error => console.error("Error:", error));
}
