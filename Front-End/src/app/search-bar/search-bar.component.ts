import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {StorageService} from "../_services/storage.service";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchTag : string = ''
  isLoggedIn : boolean | undefined
  user : any
  constructor(private router:Router, private route : ActivatedRoute, private storageService : StorageService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn()
    this.user = this.storageService.getUser()
  }
  submitSearch() {
    if (this.searchTag != null && this.searchTag != "") {
      console.log(this.searchTag)
      const route = '/questions/tagged/' + this.searchTag
      this.searchTag = '[' + this.searchTag + ']'
      if (!this.router.navigate([route])) {
        window.location.reload()
      }
    }
  }

}
