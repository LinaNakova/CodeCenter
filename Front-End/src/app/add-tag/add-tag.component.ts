import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {CodeService} from "../code.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-add-tag',
  templateUrl: './add-tag.component.html',
  styleUrls: ['./add-tag.component.css']
})
export class AddTagComponent implements OnInit {
  title="Add A Tag";
  form:FormGroup;
  constructor(public fb : FormBuilder, private service:CodeService, private router:Router) {
    this.form = this.fb.group({
      name:[""],
      description:[""]
    })
  }

  ngOnInit(): void {
  }
  submitForm()
  {
    this.service.postTag(
      this.form.get('name')?.value,
      this.form.get('description')?.value,
    );
    this.router.navigate(['/tags'])
  }

}
