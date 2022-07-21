import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {
  searchTag : string = ''
  constructor(private router:Router) { }

  ngOnInit(): void {
  }
  submitSearch()
  {
    console.log(this.searchTag)
    const route = '/questions/tagged/' + this.searchTag
    this.searchTag = '[' + this.searchTag + ']'
    this.router.navigate([route])
  }

}
