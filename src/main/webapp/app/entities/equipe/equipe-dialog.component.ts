import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Equipe } from './equipe.model';
import { EquipePopupService } from './equipe-popup.service';
import { EquipeService } from './equipe.service';

@Component({
    selector: 'jhi-equipe-dialog',
    templateUrl: './equipe-dialog.component.html'
})
export class EquipeDialogComponent implements OnInit {

    equipe: Equipe;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private equipeService: EquipeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.equipe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.equipeService.update(this.equipe));
        } else {
            this.subscribeToSaveResponse(
                this.equipeService.create(this.equipe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Equipe>>) {
        result.subscribe((res: HttpResponse<Equipe>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Equipe) {
        this.eventManager.broadcast({ name: 'equipeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-equipe-popup',
    template: ''
})
export class EquipePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private equipePopupService: EquipePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.equipePopupService
                    .open(EquipeDialogComponent as Component, params['id']);
            } else {
                this.equipePopupService
                    .open(EquipeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
