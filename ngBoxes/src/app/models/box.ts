export class Box {
  id: number;
  name: string;
  room: string;
  content: string;
  isFragile: boolean;
  img1Url: string;
  img2Url: string;
  qrCode: string;


  constructor(
    id?: number,
    name?: string,
    room?: string,
    content?: string,
    isFragile?: boolean,
    img1Url?: string,
    img2Url?: string,
    qrCode?: string
    ) {
      this.id = id;
      this.name = name;
      this.room = room;
      this.content = content;
      this.isFragile = isFragile;
      this.img1Url = img1Url;
      this.img2Url = img2Url;
      this.qrCode = qrCode;
  }
}
