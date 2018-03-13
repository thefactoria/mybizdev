/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MybizdevTestModule } from '../../../test.module';
import { EtatDetailComponent } from '../../../../../../main/webapp/app/entities/etat/etat-detail.component';
import { EtatService } from '../../../../../../main/webapp/app/entities/etat/etat.service';
import { Etat } from '../../../../../../main/webapp/app/entities/etat/etat.model';

describe('Component Tests', () => {

    describe('Etat Management Detail Component', () => {
        let comp: EtatDetailComponent;
        let fixture: ComponentFixture<EtatDetailComponent>;
        let service: EtatService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [EtatDetailComponent],
                providers: [
                    EtatService
                ]
            })
            .overrideTemplate(EtatDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EtatDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EtatService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Etat(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.etat).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
