import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }
  register(form) {
    console.log(form.value);

    this.authService.register(form.value).subscribe(
      data => {
        console.log('RegisterComponent.register(): user registered.');
        this.authService.login(form.value.username, form.value.password).subscribe(
          next => {
            console.log('RegisterComponent.register(): user logged in, routing to /todo.');
            this.router.navigateByUrl('/locations');
          },
          error => {
            console.error('RegisterComponent.register(): error logging in.');
          }
        );
      },
      err => {
        console.error('RegisterComponent.register(): error registering.');
        console.error(err);
      }
    );
  }
}
