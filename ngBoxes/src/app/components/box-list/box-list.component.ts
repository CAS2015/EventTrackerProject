import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Box } from 'src/app/models/box';
import { AuthService } from 'src/app/services/auth.service';
import { BoxService } from 'src/app/services/box.service';
import generateQr from '../../utils/qrcode.js';

@Component({
  selector: 'app-box-list',
  templateUrl: './box-list.component.html',
  styleUrls: ['./box-list.component.css']
})
export class BoxListComponent implements OnInit {

  boxes: Box[] = [];
  selected: Box = null;
  addBox: boolean = false;
  update: boolean = false;

  constructor(private boxService: BoxService, private router: Router,
    private authService: AuthService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadBoxes();
  }

  loadBoxes(): void {
    let locId= +this.route.snapshot.paramMap.get('id');

    this.boxService.index(locId).subscribe(
      boxes => {
        this.boxes = boxes;
      },
      fail => {
        console.error('BoxListComponent.loadBoxes() failed:');
        console.error(fail);
      }
    );
  }

  boxDetails(box) {
    this.selected = box;


    generateQr(+this.route.snapshot.paramMap.get('id'), this.selected.id);
  }

  back() {
    this.selected = null;
    this.addBox = false;
    this.update = false;
    this.loadBoxes();
    let div = document.getElementById("qrcode");
    div.textContent = '';
  }

  addNew() {
    this.update = false;
    this.addBox = true;
    this.selected = new Box();
  }

  createBox(box) {
    this.boxService.create(box).subscribe(
      data => {
        this.addBox = false;
        this.boxDetails(data);
      },
      fail => {
        console.error('BoxListComponent.createBox() failed:');
        console.error(fail);
      }
    )
  }

  updateBox(box) {
    let div = document.getElementById("qrcode");
    div.textContent = '';
    this.addBox = true;
    this.update = true;
    this.selected = box;
  }

  deleteBox(id) {
    this.boxService.destroy(id).subscribe(
      data => {
        this.addBox = false;
        this.update = false;
        this.selected = null;
        let div = document.getElementById("qrcode");
        div.textContent = '';
        this.loadBoxes();
      },
      fail => {
        console.error('BoxListComponent.destroyBox() failed:');
        console.error(fail);
      }
    )
  }
}
