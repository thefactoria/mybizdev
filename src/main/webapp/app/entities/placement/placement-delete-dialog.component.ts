import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Placement } from './placement.model';
import { PlacementPopupService } from './placement-popup.service';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement-delete-dialog',
    templateUrl: './placement-delete-dialog.component.html'
})
export class PlacementDeleteDialogComponent {

    placement: Placement;

    constructor(
        private placementService: PlacementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.placementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'placementListModification',
                content: 'Deleted an placement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-placement-delete-popup',
    template: ''
})
export class PlacementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placementPopupService: PlacementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.placementPopupService
                .open(PlacementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
