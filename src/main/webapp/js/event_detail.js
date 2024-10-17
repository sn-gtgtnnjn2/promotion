/**
 * 
 */

function submitSignUpForm() {
	document.getElementById('signUpForm').submit();
}
function openSearchDiv() {
	document.getElementById('overlay').style.display = 'block';
	document.getElementById('childScreen').style.display = 'block';
}

function closeSearchDiv() {
	document.getElementById('overlay').style.display = 'none';
	document.getElementById('childScreen').style.display = 'none';
}

document.addEventListener("DOMContentLoaded", function() {
	document.getElementById('overlay').addEventListener('click', function() {
		console.log('Overlay clicked');
		closeSearchDiv();
	});
});

//function fetchSuggestions() {
//	const query = document.getElementById('characterName').value;
//	if (query.length < 1) {
//		document.getElementById('suggestions').style.display = 'none';
//		return;
//	}
//
//	const xhr = new XMLHttpRequest();
//	xhr.open('GET', '/SearchSuggestionsServlet?query=' + encodeURIComponent(query), true);
//		alert("queryああ");
//	xhr.onreadystatechange = function() {
//		if (xhr.readyState === 4 && xhr.status === 200) {
//			const suggestions = JSON.parse(xhr.responseText);
//			const suggestionsList = document.getElementById('suggestions');
//			suggestionsList.innerHTML = '';
//			suggestions.forEach(function(suggestion) {
//				const li = document.createElement('li');
//				li.textContent = suggestion.name;
//				li.onclick = function() {
//					document.getElementById('characterName').value = suggestion.name;
//					displayCharacterDetails(suggestion.characterId);
//					suggestionsList.style.display = 'none';
//				};
//				suggestionsList.appendChild(li);
//			});
//			suggestionsList.style.display = 'block';
//		}
//	};
//	xhr.send
//}

function fetchSuggestions() {
    const query = document.getElementById('characterName').value;
    if (query.length < 1) {
        document.getElementById('suggestions').style.display = 'none';
        return;
    }

    const xhr = new XMLHttpRequest();
    xhr.open('GET', ctx + '/SearchSuggestionsServlet?query=' + encodeURIComponent(query), true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const suggestions = JSON.parse(xhr.responseText);
            const suggestionsList = document.getElementById('suggestions');
            suggestionsList.innerHTML = '';
            suggestions.forEach(function(suggestion) {
                const li = document.createElement('li');
                li.textContent = suggestion.name;
                li.onclick = function() {
                    document.getElementById('characterName').value = suggestion.name;
                    displayCharacterDetails(suggestion.characterId);
                    suggestionsList.style.display = 'none';
                };
                suggestionsList.appendChild(li);
            });
            suggestionsList.style.display = 'block';
        }
    };
    xhr.send();
}

function displayCharacterDetails(characterId) {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', ctx + '/CharacterDetailsServlet?characterId=' + encodeURIComponent(characterId), true);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const characterInfo = JSON.parse(xhr.responseText);
            document.getElementById('characterInfo').innerText = characterInfo.name + " (" + characterInfo.nameKana + ")";
            document.getElementById('characterId').value = characterInfo.characterId;
            document.getElementById('characterDetails').style.display = 'block';
        }
    };
    xhr.send();
}

function registerParticipant() {
    const charId = document.getElementById('characterId').value;
    const charName = document.getElementById('characterName').value;
    const eventId = document.getElementById('eventId').value;
    const body = JSON.stringify({
        characterName: charName,
        characterId: charId,
        eventId: eventId
    });

    $.post(ctx + `/AddPerticipateCharaServlet`, body, function(data, status) {
        if(status === "success"){
            console.log(data);
        } else {
            console.log("登録失敗");
        }
    }, "json").done(function(data) {
		console.log("完了: ", data);
		updateCharacterList()
		clearInputFields();
	}).fail(function(xhr, status, error) {
		console.error("リクエストエラー: ", error);
		console.log("ステータス: ", status);
		console.log("レスポンス: ", xhr.responseText);
	});

	closeSearchDiv();
}

//function registerParticipant() {
//    if (window.opener && !window.opener.closed) {
//        const characterName = document.getElementById('characterName').value;
//        window.opener.document.getElementById('selectedCharacter').innerText = characterName;
//        closeSearchDiv();
//    } else {
//        console.error('Parent window is not accessible or has been closed.');
//    }
//}

function clearInputFields() {
    document.getElementById('characterName').value = '';
    document.getElementById('characterId').value = '';
    document.getElementById('suggestions').style.display = 'none';
    document.getElementById('characterDetails').style.display = 'none';
    document.getElementById('characterInfo').innerText = '';
}

// クリアボタンに追加する場合
// <button type="button" onclick="clearInputFields()">クリア</button>

function searchCharacter() {
	// 検索処理をここに追加
	closeSearchDiv();
}

function openRegisterDiv() {
	// 新規登録画面を表示する処理
}

function viewCharacterDetails(characterId) {
    window.location.href = '/characterDetails?characterId=' + characterId;
}

function deleteCharacter(event, characterId) {
    event.stopPropagation(); // 親のクリックイベントを防止
    if (confirm('このキャラクターを削除しますか？')) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', ctx + '/DeleteCharacterServlet', true);
        xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // 削除成功
                document.querySelector('.character-card[data-id="' + characterId + '"]').remove();
            } else if (xhr.readyState === 4) {
                // 削除失敗
                alert('削除に失敗しました。');
            }
        };
        xhr.send(JSON.stringify({ characterId: characterId }));
    }
}

function updateCharacterList() {
	const eventId = document.getElementById('eventId').value;
    $.ajax({
        url: ctx + '/UpdateCharaListServlet?eventId=' + eventId, // サーブレットのURL
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            const charaListForScreen = response.charaListForScreen;
            const characterContainer = $('#characterContainer');
            characterContainer.empty(); // 現在の内容をクリア
            console.log(charaListForScreen);
            console.log(response);
			if((response !== undefined) && response !== null && response !== ""){				
				response.forEach(function(chara, index) {
					const characterCard = `
                    <div class="character-card" onclick="viewCharacterDetails('${index}')">
                        <img src="`+ctx+`/${chara.imageFilePath}" alt="キャラクター画像" class="character-image">
                        <span class="delete-button" onclick="deleteCharacter(event, '${index}')">×</span>
                        <p class="character-name">${chara.name}</p>
                        <p class="player-name">${chara.playerName}</p>
                    </div>
                `;
					characterContainer.append(characterCard);
				});
			}
        },
        error: function(xhr, status, error) {
            console.error('エラー: ' + error);
        }
    });
}
