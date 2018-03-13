import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Placement } from './placement.model';
import { PlacementPopupService } from './placement-popup.service';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement-dialog',
    templateUrl: './placement-dialog.component.html'
})
export class PlacementDialogComponent implements OnInit {

    placement: Placement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private placementService: PlacementService,
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
        if (this.placement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.placementService.update(this.placement));
        } else {
            this.subscribeToSaveResponse(
                this.placementService.create(this.placement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Placement>>) {
        result.subscribe((res: HttpResponse<Placement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Placement) {
        this.eventManager.broadcast({ name: 'placementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-placement-popup',
    template: ''
})
export class PlacementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placementPopupService: PlacementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.placementPopupService
                    .open(PlacementDialogComponent as Component, params['id']);
            } else {
                this.placementPopupService
                    .open(PlacementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
