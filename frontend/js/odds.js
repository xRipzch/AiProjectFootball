const teamForm = document.getElementById("input-form");
const baseUrl = "http://localhost:8080";

teamForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const league = document.getElementById("leagues").value;
    const region = document.getElementById("regions").value;
    const teamName = document.getElementById("team").value;

    const oddsJsonOutput = await fetchTeamOdds2(league, region, teamName);
    console.log(oddsJsonOutput)
    const chatJsonOutput = await fetchChatGPTPrompt(oddsJsonOutput);
    console.log(chatJsonOutput);
});

async function fetchTeamOdds(league, region, team) {
    const oddsUrl = baseUrl + "/football/odds/h2h/leagues/" + league + "/regions/" + region + "/teams/" + team;
    return await fetch(oddsUrl, { mode: 'cors' })  // Set mode to 'cors'
        .then(response => response.json());
}

async function fetchTeamOdds2(league, region, team) {
    const oddsUrl = baseUrl + "/football/odds/h2h";
    const requestData = { league, region, team };

    // Send the request with POST and the requestData in the body
    return await fetch(oddsUrl, {
        method: 'POST', // Change to POST
        headers: {
            'Content-Type': 'application/json', // Indicate the data is in JSON format
        },
        body: JSON.stringify(requestData), // Convert the request data to a JSON string
    })
        .then(response => response.json());
}

async function fetchChatGPTPrompt(oddsJsonOutput) {
    const promptChatUrl = baseUrl + "/chat";
    try {
        const response = await fetch(promptChatUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ message: oddsJsonOutput })  // Send JSON data in the body
        });
        return await response.json();
    } catch (error) {
        console.error("Error fetching ChatGPT prompt:", error);
    }
}