
function openForm() {
    document.getElementById('form-container').style.display = 'block';
    document.getElementById('overlay').style.display = 'block';
}

function previewImage(event) {
    const reader = new FileReader();
    reader.onload = function() {
        const preview = document.getElementById('image-preview');
        preview.src = reader.result;
        preview.style.display = 'block';
    }
    reader.readAsDataURL(event.target.files[0]);
}

window.onclick = function(event) {
    const formContainer = document.getElementById('form-container');
    const overlay = document.getElementById('overlay');
    if (event.target === overlay) {
        formContainer.style.display = 'none';
        overlay.style.display = 'none';
    }
    
    const profTextFormContainer = document.getElementById('prof-text-container');
    const profTextoverlay = document.getElementById('prof-text-overlay');
    if (event.target === profTextoverlay) {
        profTextFormContainer.style.display = 'none';
        profTextoverlay.style.display = 'none';
    }
}

function openEditDialog() {
    document.getElementById('prof-text-container').style.display = 'block';
    document.getElementById('prof-text-overlay').style.display = 'block';
}

function openEditDialogWithError(profText) {
    document.getElementById('prof-text-container').style.display = 'block';
    document.getElementById('prof-text-overlay').style.display = 'block';
    document.getElementById('profText').textContent = profText;
}

function closeEditDialog() {
    document.getElementById('edit-dialog').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';
}

function saveContent() {
    const content = document.getElementById('edit-content').value;
    // 保存処理をここに追加
    closeEditDialog();
}
//
//function openChildWindowWithError(errorMessage, prfText) {
//	openEditDialog(errorMessage, prfText);
//}