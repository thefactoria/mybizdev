import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Consultant } from './consultant.model';
import { ConsultantPopupService } from './consultant-popup.service';
import { ConsultantService } from './consultant.service';

@Component({
    selector: 'jhi-consultant-dialog',
    templateUrl: './consultant-dialog.component.html'
})
export class ConsultantDialogComponent implements OnInit {

    consultant: Consultant;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private consultantService: ConsultantService,
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
        if (this.consultant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.consultantService.update(this.consultant));
        } else {
            this.subscribeToSaveResponse(
                this.consultantService.create(this.consultant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Consultant>>) {
        result.subscribe((res: HttpResponse<Consultant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Consultant) {
        this.eventManager.broadcast({ name: 'consultantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-consultant-popup',
    template: ''
})
export class ConsultantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private consultantPopupService: ConsultantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.consultantPopupService
                    .open(ConsultantDialogComponent as Component, params['id']);
            } else {
                this.consultantPopupService
                    .open(ConsultantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
