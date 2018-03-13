/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MybizdevTestModule } from '../../../test.module';
import { EtatComponent } from '../../../../../../main/webapp/app/entities/etat/etat.component';
import { EtatService } from '../../../../../../main/webapp/app/entities/etat/etat.service';
import { Etat } from '../../../../../../main/webapp/app/entities/etat/etat.model';

describe('Component Tests', () => {

    describe('Etat Management Component', () => {
        let comp: EtatComponent;
        let fixture: ComponentFixture<EtatComponent>;
        let service: EtatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [EtatComponent],
                providers: [
                    EtatService
                ]
            })
            .overrideTemplate(EtatComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Etat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.etats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
