// import {apiCallWithFullUrl} from "../api/apicall";

const teamForm = document.getElementById("input-form");
const baseUrl = "http://localhost:8080";

teamForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const league = document.getElementById("leagues").value;
    const region = document.getElementById("regions").value;
    const teamName = document.getElementById("team").value;

    const oddsJsonOutput = await fetchTeamOdds2(league, region, teamName);
    console.log(oddsJsonOutput);

    const chatJsonOutput = await fetchChatGPTPrompt(oddsJsonOutput);
    console.log(chatJsonOutput);
    const chatHTMLOutput = chatJsonOutput

    displayMatchCards(oddsJsonOutput.matches, chatHTMLOutput); // Assuming `matches` is an array of match data
});

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
    }).then(response => response.json());
}

async function fetchChatGPTPrompt(oddsJsonOutput) {
    const promptChatUrl = baseUrl + "/chat";
    try {
        const response = await fetch(promptChatUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ message: oddsJsonOutput }) // Send JSON data in the body
        });
        return await response.json();
    } catch (error) {
        console.error("Error fetching ChatGPT prompt:", error);
    }
}

// Function to dynamically display match cards
function displayMatchCards(matches) {
    const matchContainer = document.createElement('div');
    matchContainer.id = "match-container";
    matchContainer.style.display = "grid";
    matchContainer.style.gridTemplateColumns = "repeat(auto-fit, minmax(300px, 1fr))";
    matchContainer.style.gap = "20px";
    matchContainer.style.marginTop = "20px";

    // Clear existing cards
    document.body.querySelector('#match-container')?.remove();

    // Append container to body
    document.body.appendChild(matchContainer);

    matches.forEach(match => {
        const card = document.createElement('div');
        card.className = 'match-card';
        card.style.background = "#fff";
        card.style.border = "1px solid #ddd";
        card.style.borderRadius = "8px";
        card.style.boxShadow = "0 4px 10px rgba(0, 0, 0, 0.1)";
        card.style.padding = "20px";
        card.style.textAlign = "center";

        card.innerHTML =

        matchContainer.appendChild(card);
    });
}
