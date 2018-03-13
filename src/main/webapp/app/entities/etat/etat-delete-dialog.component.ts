import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Etat } from './etat.model';
import { EtatPopupService } from './etat-popup.service';
import { EtatService } from './etat.service';

@Component({
    selector: 'jhi-etat-delete-dialog',
    templateUrl: './etat-delete-dialog.component.html'
})
export class EtatDeleteDialogComponent {

    etat: Etat;

    constructor(
        private etatService: EtatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.etatService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'etatListModification',
                content: 'Deleted an etat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-etat-delete-popup',
    template: ''
})
export class EtatDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private etatPopupService: EtatPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.etatPopupService
                .open(EtatDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
