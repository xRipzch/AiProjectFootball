const teamForm = document.getElementById("input-form");
const baseUrl = "http://localhost:8080";

teamForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    const teamName = document.getElementById("team").value;
    const oddsJsonOutput = await fetchTeamOdds(teamName);
    console.log(oddsJsonOutput)
    const chatJsonOutput = await fetchChatGPTPrompt(oddsJsonOutput);
    console.log(chatJsonOutput);
});

async function fetchTeamOdds(team) {
    const oddsUrl = baseUrl + "/football/odds/teams/" + team;
    return await fetch(oddsUrl, { mode: 'cors' })  // Set mode to 'cors'
        .then(response => response.json());
}

// Doesn't work properly
async function fetchChatGPTPrompt2(oddsJsonOutput) {
    const test = JSON.stringify(oddsJsonOutput);
    console.log(test)
    const promptChatUrl = `${baseUrl}/chat/${test}`;

    return await fetch(promptChatUrl, { mode: 'cors' })  // Set mode to 'cors'
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