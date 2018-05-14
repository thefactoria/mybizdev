import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Placement } from './placement.model';
import { PlacementPopupService } from './placement-popup.service';
import { PlacementService } from './placement.service';

@Component({
    selector: 'jhi-placement-archive-dialog',
    templateUrl: './placement-archive-dialog.component.html'
})
export class PlacementArchiveDialogComponent {

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

    confirmArchive(id: number) {
        this.placementService.archive(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'placementListModification',
                content: 'Archived an placement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-placement-archive-popup',
    template: ''
})
export class PlacementArchivePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private placementPopupService: PlacementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.placementPopupService
                .open(PlacementArchiveDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
