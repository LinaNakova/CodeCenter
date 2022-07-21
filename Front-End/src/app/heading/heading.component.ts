import {Component, Input, OnInit,} from '@angular/core';

@Component({
  selector: 'app-heading',
  templateUrl: './heading.component.html',
  styleUrls: ['./heading.component.css']
})
export class HeadingComponent implements OnInit {
  @Input()
  title : string | undefined;
  constructor() { }

  ngOnInit(): void {
  }

}
