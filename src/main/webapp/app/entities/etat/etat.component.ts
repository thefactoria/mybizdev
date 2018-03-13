import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Etat } from './etat.model';
import { EtatService } from './etat.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-etat',
    templateUrl: './etat.component.html'
})
export class EtatComponent implements OnInit, OnDestroy {
etats: Etat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private etatService: EtatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.etatService.query().subscribe(
            (res: HttpResponse<Etat[]>) => {
                this.etats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEtats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Etat) {
        return item.id;
    }
    registerChangeInEtats() {
        this.eventSubscriber = this.eventManager.subscribe('etatListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
