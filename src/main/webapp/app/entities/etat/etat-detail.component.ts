import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Etat } from './etat.model';
import { EtatService } from './etat.service';

@Component({
    selector: 'jhi-etat-detail',
    templateUrl: './etat-detail.component.html'
})
export class EtatDetailComponent implements OnInit, OnDestroy {

    etat: Etat;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private etatService: EtatService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEtats();
    }

    load(id) {
        this.etatService.find(id)
            .subscribe((etatResponse: HttpResponse<Etat>) => {
                this.etat = etatResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEtats() {
        this.eventSubscriber = this.eventManager.subscribe(
            'etatListModification',
            (response) => this.load(this.etat.id)
        );
    }
}
