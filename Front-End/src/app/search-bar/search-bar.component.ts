import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {StorageService} from "../_services/storage.service";
import {Input} from "@angular/core";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchTag: string = ''
  @Input()
  isLoggedIn = false;


  constructor(private router: Router, private storage: StorageService) {
    console.log('fun is logged in ' + this.storage.isLoggedIn())
    this.isLoggedIn = storage.isLoggedIn();
  }

  ngOnInit(): void {
  }

  submitSearch() {
    console.log(this.searchTag)
    const route = '/questions/tagged/' + this.searchTag
    this.searchTag = '[' + this.searchTag + ']'
    this.router.navigate([route])
  }

  logIn() {
    this.router.navigate(['/login']);
  }

}
