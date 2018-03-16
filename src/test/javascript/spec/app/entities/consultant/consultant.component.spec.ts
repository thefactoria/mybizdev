/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MybizdevTestModule } from '../../../test.module';
import { ConsultantComponent } from '../../../../../../main/webapp/app/entities/consultant/consultant.component';
import { ConsultantService } from '../../../../../../main/webapp/app/entities/consultant/consultant.service';
import { Consultant } from '../../../../../../main/webapp/app/entities/consultant/consultant.model';

describe('Component Tests', () => {

    describe('Consultant Management Component', () => {
        let comp: ConsultantComponent;
        let fixture: ComponentFixture<ConsultantComponent>;
        let service: ConsultantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MybizdevTestModule],
                declarations: [ConsultantComponent],
                providers: [
                    ConsultantService
                ]
            })
            .overrideTemplate(ConsultantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConsultantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConsultantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Consultant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.consultants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
