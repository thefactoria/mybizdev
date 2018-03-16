import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BizDev } from './biz-dev.model';
import { BizDevPopupService } from './biz-dev-popup.service';
import { BizDevService } from './biz-dev.service';

@Component({
    selector: 'jhi-biz-dev-delete-dialog',
    templateUrl: './biz-dev-delete-dialog.component.html'
})
export class BizDevDeleteDialogComponent {

    bizDev: BizDev;

    constructor(
        private bizDevService: BizDevService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bizDevService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bizDevListModification',
                content: 'Deleted an bizDev'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-biz-dev-delete-popup',
    template: ''
})
export class BizDevDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bizDevPopupService: BizDevPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bizDevPopupService
                .open(BizDevDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
