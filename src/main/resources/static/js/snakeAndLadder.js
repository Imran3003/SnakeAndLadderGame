let player = 1;

async function selectPlayers(number) {
    console.log("Number = ", number);
    localStorage.setItem('numberOfPlayers', number); // Store number of players in local storage
    window.location.href = "/selectNumberOfPlayers?numberOfPlayers=" + number;
}

async function diceClick() {
    let numberOfPlayers = localStorage.getItem('numberOfPlayers');
    console.log("Number of Players:", numberOfPlayers);

    const dice = document.getElementById('dice');
    if (!dice) {
        console.error("Dice element not found");
        return;
    }

    // Mock backend call to get dice value
    const diceValue = await generateRandomDizeValue();
    console.log("Dice Value:", diceValue);

    // Apply random rotation before showing the specific face
    const randomRotation = getRandomRotation();
    const specificRotation = getRotationAngles(diceValue);

    // First, apply a random rotation
    dice.style.transform = `rotateX(${randomRotation.x}deg) rotateY(${randomRotation.y}deg)`;

    // Apply the specific rotation after a delay to ensure it appears as a separate rotation
    setTimeout(() => {
        dice.style.transform = `rotateX(${specificRotation.x}deg) rotateY(${specificRotation.y}deg)`;
        console.log("Dice Value = ", diceValue, " :: Rotation Angles = ", specificRotation);
    }, 500);

    setTimeout(async () => {
        console.log("Inside setTimeout block");

        if (player > numberOfPlayers) {
            player = 1;
        }

        let chance = getChance(player, numberOfPlayers);
        document.getElementById("h3").innerText = `PLAYER-${chance}`;

        let response = await passValueToAPI(diceValue, player);
        console.log("API Response:", response);

        if (response.playerFromPosition === 0 && response.playerPosition === 0 && response.playerName === null) {
            console.log("Player position > 100");
            player++
            return;
        }

        let fromPosition = response.playerFromPosition;
        let toValue = response.playerPosition;

        // Add more debugging logs
        console.log("Dice Value:", diceValue);
        console.log("Player From Position:", fromPosition);
        console.log("Player Position (toValue):", toValue);
        console.log("Type of toValue:", typeof toValue);

        // Convert toValue to a number
        toValue = Number(toValue);

        if (isNaN(toValue)) {
            console.error(`Invalid toValue: ${toValue}`);
            return;
        }

        if (toValue  === 100) {
            console.log("toValue");
            gameOver(player);
            return;
        }

        let coin = document.getElementById(`coin_${player}`);
        if (!coin) {
            console.error(`Element with ID coin_${player} not found`);
            return;
        }

        let fromPositionElement = document.getElementById(fromPosition + diceValue);
        if (!fromPositionElement) {
            console.error(`Element with ID ${fromPosition + diceValue} not found`);
            return;
        }

        fromPositionElement.appendChild(coin);

        let toPosition = document.getElementById(toValue);
        if (!toPosition) {
            console.error(`Element with ID ${toValue} not found`);
            return;
        }

        setTimeout(() => {
            toPosition.appendChild(coin);
            console.log("To position after change = ", toPosition);
        }, 900);

        player++;
    }, 1500);
}

function getChance(p, np) {
    let fp = p + 1;
    if (fp > np) return 1;
    return fp;
}

async function generateRandomDizeValue() {
    // Mocking the backend call. Replace this with actual backend API call.
    return new Promise((resolve) => {
        setTimeout(() => {
            const mockValue = Math.floor(Math.random() * 6) + 1; // Random value between 1 and 6
            resolve(mockValue);
        }, 500);
    });
}

async function passValueToAPI(value, player) {
    try {
        console.log(`Fetching API with result=${value} and playerNum=${player}`);
        const response = await fetch(`/rotateDize?result=${value}&playerNum=${player}`);
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        const result = await response.json();
        console.log("API Response:", result);
        return result;
    } catch (error) {
        console.error('Error in passValueToAPI:', error);
        return { playerPosition: 0 }; // Ensure fallback value
    }
}

function getRotationAngles(value) {
    switch (value) {
        case 1: return { x: 0, y: 0 };
        case 2: return { x: 0, y: -90 };
        case 3: return { x: -90, y: 0 };
        case 4: return { x: 0, y: 90 };
        case 5: return { x: 90, y: 0 };
        case 6: return { x: 0, y: 180 };
        default: return { x: 0, y: 0 };
    }
}

function getRandomRotation() {
    const randomX = Math.floor(Math.random() * 360); // Random angle between 0 and 360 degrees
    const randomY = Math.floor(Math.random() * 360); // Random angle between 0 and 360 degrees
    return { x: randomX, y: randomY };
}

function gameOver(winningPlayer) {
    console.log("Function gameOver called");
    fetch(`/GameOver?player=${encodeURIComponent(winningPlayer)}`, {
        method: 'GET', // Use 'POST' if your API requires a POST request
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json(); // or response.text() or other methods depending on your API response
        })
        .then(data => {
            console.log('Success:', data);
            // Handle the data received from the API
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
