const video = document.getElementById('video');
const overlay = document.getElementById('overlay');
let lastPostion = '';

Promise.all([
    faceapi.nets.tinyFaceDetector.loadFromUri('/static/models'),
    faceapi.nets.faceLandmark68Net.loadFromUri('/static/models')
]).then(startVideo);

function startVideo() {
    navigator.mediaDevices.getUserMedia({video: {}})
        .then(stream => {
            video.srcObject = stream;
            video.style.transform = "scaleX(-1)"; //좌우 반전(거울 효과)
            video.addEventListener('loadeddata', () => {
                video.play();
                onPlay();
        });
    })
    .catch(err => console.error(err));
}
async function onPlay() {
    const displaySize = {width: video.videoWidth, height:video.videoHeight};
    faceapi.matchDimensions(overlay, displaySize);

    const context = overlay.getContext('2d');
    context.setTransform(-1, 0, 0, 1, overlay.width, 0); // 캔버스에 거울 효과 적용

    setInterval(async () => {
        if (video.paused || video.ended) {
            return;
        }

        const detections = await faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).withFaceLandmarks();
        const resizedDetections = faceapi.resizeResults(detections, displaySize);

        context.clearRect(0,0, overlay.width, overlay.height);

        //얼굴 랜드마크를 그리고 감지 박스를 제거
        faceapi.draw.drawFaceLandmarks(overlay, resizedDetections);

        //수직선을 그리기
        context.strokeStyle = 'red';
        context.beginPath();
        context.moveTo(overlay.width / 2,0);
        context.lineTo(overlay.width / 2, overlay.height);
        context.stroke();

        if (resizedDetections.length > 0) {
            const landmarks = resizedDetections[0].landmarks;
            const nose = landmarks.getNose();
            const noseX = overlay.width - nose[3].x; //거울 효과로 인해 좌표를 반전

            if (noseX < overlay.width / 2 && lastPostion !== 'left') {
                lastPostion = 'left';
                alert('선택한 답 : O');
                sendAnswer('O');
            } else if (noseX >= overlay.width / 2 && lastPostion !== 'right') {
                lastPostion = 'right';
                alert('선택한 답 : X');
                sendAnswer('X')
            }
        }
    }, 100)

}
function sendAnswer(answer) {
    const urlParams = new URLSearchParams(window.location.search);
    const level = urlParams.get('level');
    const number = urlParams.get('number');

    fetch("/face/grading", {
        method : "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body:JSON.stringify({answer: answer, level: level, number: number})
    })
        .then(response => response.json())
        .then(data => {
            if (data.correct) {
                alert('정답입니다.');
                window.location.href = '/face/correct';
            } else {
                alert('오답입니다.');
                window.location.href = '/face/wrong';
            }
        })
        .catch((error) => {
            console.log('Error : ', error);
        });
}