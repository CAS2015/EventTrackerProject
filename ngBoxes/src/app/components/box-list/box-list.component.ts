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

}
