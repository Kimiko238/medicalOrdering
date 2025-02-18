
document.addEventListener("DOMContentLoaded", function () {
flatpickr("#datetimepicker", {
    enableTime: true,         // 時間選択を有効化
    dateFormat: "Y-m-d H:i",  // 表示フォーマット（YYYY-MM-DD HH:mm）
    time_24hr: true,          // 24時間表記
    minuteIncrement: 30,      // 30分ごとの選択
    defaultHour: 9,           // 初期時間を6時に設定
    minTime: "9:00",         // 最小時間を6:00
    maxTime: "15:30",         // 最大時間を23:30
    disableMobile: true,      // モバイルのデフォルトピッカーを無効化
    onReady: function (selectedDates, dateStr, instance) {
      customizeTimePicker(instance);
    },
    onOpen: function (selectedDates, dateStr, instance) {
      customizeTimePicker(instance);
    }
});

   flatpickr("#dateOnlyPicker", {
        enableTime: false,        // 時間を無効化
        dateFormat: "Y-m-d",      // フォーマット YYYY-MM-DD
    });

  function customizeTimePicker(instance) {
    const timeContainer = instance.timeContainer;
    if (!timeContainer) return;

// 既存の入力フィールドを非表示
    const hourInput = timeContainer.querySelector(".flatpickr-hour");
    const minuteInput = timeContainer.querySelector(".flatpickr-minute");
    if (hourInput) hourInput.style.display = "none";
    if (minuteInput) minuteInput.style.display = "none";

// 既存のセレクタがある場合は削除
    const existingHourSelect = timeContainer.querySelector(".custom-hour-select");
    const existingMinuteSelect = timeContainer.querySelector(".custom-minute-select");
    if (existingHourSelect) existingHourSelect.remove();
    if (existingMinuteSelect) existingMinuteSelect.remove();

// 時間セレクトボックスを作成
    const hourSelect = document.createElement("select");
    hourSelect.className = "custom-hour-select";
    for (let i = 9; i <= 15; i++) {
    let option = document.createElement("option");
    option.value = i;
    option.textContent = i.toString().padStart(2, "0");
    hourSelect.appendChild(option);
  }
  hourSelect.addEventListener("change", function () {
    instance.setDate(new Date(instance.selectedDates[0].setHours(this.value)));
  });

// 分セレクトボックスを作成（30分単位）
  const minuteSelect = document.createElement("select");
  minuteSelect.className = "custom-minute-select";
  [0, 30].forEach(min => {
    let option = document.createElement("option");
    option.value = min;
    option.textContent = min.toString().padStart(2, "0");
    minuteSelect.appendChild(option);
  });
  minuteSelect.addEventListener("change", function () {
    instance.setDate(new Date(instance.selectedDates[0].setMinutes(this.value)));
  });

// 時間選択エリアに追加
  timeContainer.appendChild(hourSelect);
  timeContainer.appendChild(document.createTextNode(" : "));
  timeContainer.appendChild(minuteSelect);
  }
});



function setStatus(status) {
        document.getElementById("clickStatus").value = status;
    }



