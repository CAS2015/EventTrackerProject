import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from 'src/app/models/location';
import { AuthService } from 'src/app/services/auth.service';
import { LocationService } from 'src/app/services/location.service';

@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {

  locations: Location[] = [];
  newLocation: Location = new Location();
  updateLocation: Location = null;

  constructor(private locService: LocationService, private router: Router,
    private authService: AuthService) { }

  ngOnInit(): void {
    this.loadLocations();
  }

  loadLocations(): void {

    this.loginCheck();

    this.locService.index().subscribe(

      locations => {
        this.locations = locations;
        this.newLocation = new Location();
      },
      fail => {
        console.error('LocationListComponent.loadLocations() failed:');
        console.error(fail);
      }
    );
  }

  listBoxes(locId: number) {
    this.loginCheck();
    this.router.navigateByUrl(`locations/${locId}/boxes`)
  }

  addNewLocation() {
    this.loginCheck();
    this.locService.create(this.newLocation).subscribe(
      data => {
        this.loadLocations();
      },
      fail => {
        console.error('LocationListComponent.createLocations() failed:');
        console.error(fail);
      }
    );
  }

  editLocation(loc) {
    this.loginCheck();
    this.updateLocation = loc;
  }

  completeEdit() {
    this.locService.update(this.updateLocation).subscribe(
      data => {
        this.loadLocations();
        this.updateLocation=null;
      },
      fail => {
        console.error('LocationListComponent.updateLocations() failed:');
        console.error(fail);
      }
    )
  }

  cancel() {
    this.updateLocation = null;
  }

  delete() {
    this.loginCheck();
    this.locService.destroy(this.updateLocation.id).subscribe(
      data => {
        this.loadLocations();
        this.updateLocation=null;
      },
      fail => {
        console.error('LocationListComponent.deleteLocations() failed:');
        console.error(fail);
      }
    )
  }

  private loginCheck() {
    if(!this.authService.checkLogin) {
      this.router.navigateByUrl('/home');
    }
  }
}
