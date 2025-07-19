<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
    $(document).ready(function () {
        const $checkbox = $('#autoLan');
        const $select = $('#sourceLang');
        const $selectLabel = $('.select-label');

        function updateSelectState() {
            const isDisabled = $checkbox.is(':checked');
            $select.prop('disabled', isDisabled);

            if (isDisabled) {
                $select.addClass('select-inline-disabled');
                $selectLabel.addClass('select-label-disabled');
            } else {
                $select.removeClass('select-inline-disabled');
                $selectLabel.removeClass('select-label-disabled');
            }
        }

        $checkbox.on('change', updateSelectState);

        // 초기 설정
        updateSelectState();
    });
    
    let inputCount = 1;
    const targetLangList = JSON.parse('${targetLangString}');

    function addTarget() {
        inputCount++;

        const wrapper = document.getElementById('inputLang-wrapper');

        const group = document.createElement("div");
        group.className = "input-group";
        group.id = "group_" + inputCount;

        // 1. select 영역 (div 따로)
        const selectLine = document.createElement("div");
        const select = document.createElement("select");
        select.id = "targetLang_" + inputCount;
        select.className = "select-inline";

        for (let lang of targetLangList) {
            const option = document.createElement("option");
            option.value = lang.code;
            option.text = lang.name;
            select.appendChild(option);
        }

        selectLine.style.display = "flex";
        selectLine.style.justifyContent = "flex-end";
        selectLine.style.marginBottom = "5px";
        selectLine.appendChild(select);

        // 2. textarea + delete 버튼 감싸는 div
        const textareaWrapper = document.createElement("div");
        textareaWrapper.className = "textarea-wrapper";

        const textarea = document.createElement("textarea");
        textarea.id = "inputString_" + inputCount;
        textarea.name = "inputString";
        textarea.spellcheck = false;
        textarea.placeholder = "번역할 내용을 입력하세요";

        const deleteBtn = document.createElement("button");
        deleteBtn.className = "delete-btn";
        deleteBtn.type = "button";
        deleteBtn.innerText = "x";
        deleteBtn.onclick = () => removeTarget(group.id);

        textareaWrapper.appendChild(textarea);
        textareaWrapper.appendChild(deleteBtn);

        // 최종 조립
        group.appendChild(selectLine);
        group.appendChild(textareaWrapper);
        wrapper.appendChild(group);
    }

    function removeTarget(groupId) {
        const group = document.getElementById(groupId);
        if (group) {
            group.remove();
        }
    }

    function translate() {
        myAxios.post('/dev/translate',
            {
                inputString: $("#inputString").val(),
                targetLang: $("#targetLang").val()
            }
        )
            .then(res => {
                // axios는 자동으로 JSON 파싱
                console.log(res.data.message);      //"처리 완료"
                console.log(res.data.data.name);    //"홍길동"
            })
    }

    function copyResult() {
        var element = document.getElementById('parsedJson');
        var selection = window.getSelection();
        selection.removeAllRanges();

        var range = document.createRange();
        range.selectNodeContents(element);
        selection.addRange(range);

        try {
            var success = document.execCommand('copy');
            if (success) {
                showCopySuccessMessage();
            } else {
                alert('복사에 실패했습니다...');
            }
        } catch (err) {
            alert('복사 중 오류가 발생했습니다: ' + err);
        }

        selection.removeAllRanges();
    }

    function showCopySuccessMessage() {
        var messageDiv = document.getElementById('copy-success-message');
        messageDiv.style.display = 'block';
        messageDiv.style.opacity = '1';

        // 1초 후에 서서히 사라지게
        setTimeout(function () {
            messageDiv.style.transition = 'opacity 0.5s';
            messageDiv.style.opacity = '0';
        }, 1000);

        // 1.5초 후에 display: none 처리
        setTimeout(function () {
            messageDiv.style.display = 'none';
            messageDiv.style.transition = '';
        }, 1500);
    }
</script>

<div class="container">
    <div class="left">
        <div class="toolbar">
            <span style="float:right">
                <label for="autoLan" class="checkbox-label">
                    <input type="checkbox" id="autoLan" value="autoLan" checked>
                    자동 언어 감지
                </label>
                <label for="sourceLang" class="select-label">출발언어
                    <select id="sourceLang" class="select-inline">
                        <c:forEach var="lang" items="${sourceLang}">
                            <option value="${lang.language}">${lang.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <button class="btn" onclick="translate()">Convert</button>
            </span>
        </div>
        <div id="inputLang-wrapper">
            <div class="input-group" id="group_1">
                <div style="display: flex; justify-content: flex-end; margin-bottom: 5px;">
                    <select id="targetLang_1" class="select-inline">
                        <c:forEach var="lang" items="${targetLang}">
                            <option value="${lang.language}">${lang.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="textarea-wrapper">
                    <textarea id="inputString_1" name="inputString" spellcheck="false" placeholder="번역할 내용을 입력하세요"></textarea>
                </div>
            </div>
        </div>
        <div class="add-button-wrapper">
            <button class="btn" onclick="addTarget()">+</button>
        </div>
    </div>
    <div class="right" id="result">
        <div class="copy-success-message" id="copy-success-message">✅ 복사 완료</div>
        <div class="result-wrapper">
            <button class="copy-btn" onclick="copyResult()">복사</button>
            <!-- 결과 -->
            <div id="parsedJson" class="result-container"></div>
        </div>
    </div>
</div>