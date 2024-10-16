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
	//const characterName = document.getElementById('characterName').value;
	//document.getElementById('selectedCharacter').innerText = characterName;
	// シナリオ参加キャラクター一覧に登録処理を呼び出す。
	const charId = document.getElementById('characterId').value;
	const charName = document.getElementById('characterName').value;
	const eventId = document.getElementById('eventId').value;
	const body = JSON.stringify({
		characterName: charName,
		characterId: charId,
		eventId: eventId
	});
	$.post(ctx + "/AddPerticipateCharaServlet", body, (data, status) => {
		if(status == 200){
			
			console.log(data);
		} else {
			console.log("登録失敗");
		}
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


function searchCharacter() {
	// 検索処理をここに追加
	closeSearchDiv();
}

function openRegisterDiv() {
	// 新規登録画面を表示する処理
}
