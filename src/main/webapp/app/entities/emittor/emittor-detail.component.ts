import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmittor } from 'app/shared/model/emittor.model';

@Component({
  selector: 'jhi-emittor-detail',
  templateUrl: './emittor-detail.component.html'
})
export class EmittorDetailComponent implements OnInit {
  emittor: IEmittor;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ emittor }) => {
      this.emittor = emittor;
    });
  }

  previousState() {
    window.history.back();
  }
}
