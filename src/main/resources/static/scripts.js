// scripts.js

function confirmBlock() {
            return confirm("Вы уверены, что хотите изменить статус блокировки?");
        }
function confirmSetRole() {
            return confirm("Вы уверены, что хотите установить новую роль?");
        }
function validateForm() {
        var title = document.getElementById('title').value.trim();
        var genre = document.getElementById('genre').value.trim();
        var link = document.getElementById('link').value.trim();
        var description = document.getElementById('description').value.trim();

        if (title === '' || genre === '' || link === '' || description === '') {
            alert('Пожалуйста, заполните все поля.');
            return false;
        }

        return true;
    }
function confirmDeletePublication() {
            return confirm("Вы уверены, что хотите удалить публикацию?");
        }
function confirmDeleteComment() {
            return confirm("Вы уверены, что хотите удалить комментарий?");
        }
function validateComment() {
            var commentary = document.querySelector('.comment_text').value.trim();
            if (commentary === '') {
                alert('Комментарий не может быть пустым или состоять только из пробелов.');
                return false;
            }
            return true;
        }
function confirmLogout() {
            return confirm("Вы уверены, что хотите выйти из аккаунта?");
        }