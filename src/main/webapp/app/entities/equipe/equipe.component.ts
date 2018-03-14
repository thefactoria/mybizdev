import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Equipe } from './equipe.model';
import { EquipeService } from './equipe.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-equipe',
    templateUrl: './equipe.component.html'
})
export class EquipeComponent implements OnInit, OnDestroy {
equipes: Equipe[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private equipeService: EquipeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.equipeService.query().subscribe(
            (res: HttpResponse<Equipe[]>) => {
                this.equipes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEquipes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Equipe) {
        return item.id;
    }
    registerChangeInEquipes() {
        this.eventSubscriber = this.eventManager.subscribe('equipeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
