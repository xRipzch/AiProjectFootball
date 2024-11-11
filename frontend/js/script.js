function getPrediction() {
    const team1Id = document.getElementById('team1').value;
    const team2Id = document.getElementById('team2').value;

    const url = 'http://localhost:8080/chatWithFootballData?team1=' + team1Id + '&team2=' + team2Id;

    fetch(url)
        .then(response => response.json())
        .then(data => {
            sendToGPT(data);
        })
        .catch(error => console.error('Error fetching data:', error));
}
function sendToGPT(headToHeadResults) {
    const predictionUrl = '/chatWithFootballData';

    fetch(predictionUrl + '?footballData=' + encodeURIComponent(JSON.stringify(headToHeadResults)))
        .then(response => response.json())
        .then(data => {
            document.getElementById('predictionResult').innerText = data.Choices[0].text;
        })
        .catch(error => console.error('Error getting prediction:', error));
}
