export default function generateQr(locId, boxId) {
  console.log("generating qr");

  let qrcode = new QRCode(document.getElementById("qrcode"), {
      text: `http://3.13.22.174:8080/WhatsInTheBox/api/locations/${locId}/boxes/${boxId}`, //later "https://whatsinthebox.com/${uuid}"
      width: 128,
      height: 128,
      colorDark : "#000000",
      colorLight : "#ffffff",
      correctLevel : QRCode.CorrectLevel.H
  });

}
