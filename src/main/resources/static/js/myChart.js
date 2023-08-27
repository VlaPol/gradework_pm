const charDataStr = decodeHtml(charData);
const charJsonArray = JSON.parse(charDataStr);

const arrayLength = charJsonArray.length;
const numericData = [];
const labelData = [];

for (let i = 0; i < arrayLength; i++) {
    numericData[i] = charJsonArray[i].value;
    labelData[i] = charJsonArray[i].label;
}

//********************************************************************

//doughnut chart
new Chart(document.getElementById("myDoughCHart"), {
    type: 'doughnut',
    // data for dataset
    data: {
        labels: labelData,
        datasets: [{
            label: 'My dataset',
            backgroundColor: ["#493aa1", "#ad269b", "#41929c", "#43519a", "#2e93ca"],
            data: numericData,
        }]
    },

    options: {
        responsive: true,
        plugins: {
            legend: {
                position: 'top',
            },
            title: {
                display: true,
                text: 'Project status'
            }
        }
    },
});

//[{"value":1, "label":"COMPLETED"},{"value":2, "label":"INPROGRESS"},{"value":1, "label":"NOTSTARTED"}]
function decodeHtml(html) {
    const txt = document.createElement("textarea");
    txt.innerHTML = html;
    return txt.value;
}