import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  login(form) {

    this.authService.login(form.value.username, form.value.password).subscribe(
      data => {
          console.log('LoginComponent.login(): user logged in, routing to /location.');
          this.router.navigateByUrl('/locations');
      },
      err => {
        console.error('LoginComponent.login(): error logging in.');
        console.log(err);

      }
    )
  }

}
